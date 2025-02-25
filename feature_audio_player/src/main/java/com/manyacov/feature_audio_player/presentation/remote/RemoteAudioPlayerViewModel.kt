package com.manyacov.feature_audio_player.presentation.remote

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.saveable
import com.manyacov.common.presentation.BaseViewModel
import com.manyacov.domain.avito_player.use_case.GetApiTrackUseCase
import com.manyacov.domain.avito_player.use_case.GetCurrentTrackListPartUseCase
import com.manyacov.domain.avito_player.use_case.GetNextTrackIdUseCase
import com.manyacov.domain.avito_player.use_case.GetPreviousTrackIdUseCase
import com.manyacov.domain.avito_player.use_case.GetSessionUseCase
import com.manyacov.feature_audio_player.notification_service.service.AvitoAudioServiceHandler
import com.manyacov.feature_audio_player.notification_service.service.AvitoAudioState
import com.manyacov.feature_audio_player.notification_service.service.PlayerEvent
import com.manyacov.feature_audio_player.presentation.AudioPlayerContract
import com.manyacov.feature_audio_player.presentation.model.Audio
import com.manyacov.feature_audio_player.presentation.model.PlayerTime
import com.manyacov.feature_audio_player.presentation.model.audioDummy
import com.manyacov.feature_audio_player.presentation.utils.formatTime
import com.manyacov.feature_audio_player.presentation.utils.toMediaItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RemoteAudioPlayerViewModel @Inject constructor(
    private val audioServiceHandler: AvitoAudioServiceHandler,
    private val getSessionUseCase: GetSessionUseCase,
    private val getApiTrackUseCase: GetApiTrackUseCase,
    private val getCurrentTrackListPartUseCase: GetCurrentTrackListPartUseCase,
    private val getNextTrackIdUseCase: GetNextTrackIdUseCase,
    private val getPreviousTrackIdUseCase: GetPreviousTrackIdUseCase,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<AudioPlayerContract.Event, AudioPlayerContract.State, AudioPlayerContract.Effect>() {

    override fun createInitialState() = AudioPlayerContract.State()

    var duration by savedStateHandle.saveable { mutableStateOf(0L) }
    var progress by savedStateHandle.saveable { mutableStateOf(0f) }
    var isPlaying by savedStateHandle.saveable { mutableStateOf(false) }
    var currentSelectedAudio by savedStateHandle.saveable { mutableStateOf(audioDummy) }
    var currentSelectedIndex by savedStateHandle.saveable { mutableStateOf(0) }
    var audioList by savedStateHandle.saveable { mutableStateOf(mutableListOf<Audio>()) }

    init {
        viewModelScope.launch {
            audioServiceHandler.audioState.collectLatest { mediaState ->
                when (mediaState) {
                    is AvitoAudioState.Initial -> setState { copy(isLoading = true) }
                    is AvitoAudioState.Buffering -> calculateProgressValue(mediaState.progress)
                    is AvitoAudioState.Playing -> isPlaying = mediaState.isPlaying
                    is AvitoAudioState.Progress -> {
                        calculateProgressValue(mediaState.progress)
                    }

                    is AvitoAudioState.CurrentPlaying -> {
                        currentSelectedIndex = mediaState.mediaItemIndex
                        currentSelectedAudio = audioList[mediaState.mediaItemIndex]
                    }

                    is AvitoAudioState.Ready -> {
                        duration = mediaState.duration
                        setState { copy(isLoading = false) }
                    }
                }
            }
        }
    }

    private fun setMediaItems(currentPath: String) {
        audioList.map { audio ->
            audio.toMediaItem()
        }.also {
            audioServiceHandler.setMediaItemList(it, currentPath)
        }
    }

    private fun addMediaItems(audio: Audio) {
        val mediaItem = audio.toMediaItem()
        audioServiceHandler.addNextMediaItem(currentSelectedIndex + 2, mediaItem)
    }

    private fun calculateProgressValue(currentProgress: Long) {
        progress = if (currentProgress > 0) {
            ((currentProgress.toFloat() / duration.toFloat()) * 100f)
        } else 0f


        val playingTime = formatTime(currentProgress / 1000)
        val restTime = "- ${formatTime((duration - currentProgress) / 1000)}"

        setState { copy(playerTime = PlayerTime(playingTime = playingTime, restTime = restTime)) }
    }

    override fun onCleared() {
        viewModelScope.launch {
            audioServiceHandler.onPlayerEvents(PlayerEvent.Stop)
        }
        super.onCleared()
    }


    private fun loadTrack() = viewModelScope.launch(Dispatchers.IO) {
        getSessionUseCase.invoke(GetSessionUseCase.Params).collect { info ->
            loadApiTrack(info.orEmpty())
        }
    }

    private fun loadApiTrack(id: String) = viewModelScope.launch {
        getCurrentTrackListPartUseCase.invoke(GetCurrentTrackListPartUseCase.Params(id))
            .collect { paths ->
                val list = withContext(Dispatchers.IO) {
                    paths.map { trackId ->
                        getApiTrackUseCase.invoke(
                            GetApiTrackUseCase.Params(
                                trackId
                            )
                        ).data
                    }
                }

                val trackList = list.mapNotNull { track ->
                    track?.let {
                        Audio(
                            uri = it.uri,
                            displayName = track.displayName,
                            id = track.id,
                            artist = track.artist,
                            title = track.title,
                            imageUrl = track.imageUrl,
                        )
                    }
                }

                audioList.addAll(trackList)
                setMediaItems(currentPath = id)
            }
    }

    private fun preloadNextTrack(currentTrackId: String) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            getNextTrackIdUseCase.invoke(GetNextTrackIdUseCase.Params(currentTrackId))
                .filterNotNull()
                .collect { id ->
                    val audio =
                        getApiTrackUseCase.invoke(GetApiTrackUseCase.Params(id)).data

                    audio?.let {
                        val track = Audio(
                            uri = it.uri,
                            displayName = it.displayName,
                            id = it.id,
                            artist = it.artist,
                            title = it.title,
                            imageUrl = it.imageUrl,
                        )

                        audioList.add(track)
                        audioList.removeAt(0)
                        withContext(Dispatchers.Main) { addMediaItems(track) }
                    }
                }
        }
    }

    private fun preloadPreviousTrack(currentTrackId: String) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            getPreviousTrackIdUseCase.invoke(GetPreviousTrackIdUseCase.Params(currentTrackId))
                .filterNotNull()
                .collect { id ->

                    val audio =
                        getApiTrackUseCase.invoke(GetApiTrackUseCase.Params(id)).data

                    audio?.let {
                        val track = Audio(
                            uri = it.uri,
                            displayName = it.displayName,
                            id = it.id,
                            artist = it.artist,
                            title = it.title,
                            imageUrl = it.imageUrl,
                        )

                        audioList.add(0, track)
                        audioList.removeAt(audioList.lastIndex)

                        withContext(Dispatchers.Main) {
                            addPrevMediaItems(track)
                        }
                    }
                }
        }
    }

    private fun addPrevMediaItems(audio: Audio) {
        val mediaItem = audio.toMediaItem()
        audioServiceHandler.addPreviousMediaItem(mediaItem)
    }

    override fun handleEvent(event: AudioPlayerContract.Event) {
        when (event) {
            is AudioPlayerContract.Event.OnScreenOpened -> loadTrack()

            is AudioPlayerContract.Event.OnNextClicked -> {
                viewModelScope.launch {
                    preloadNextTrack(event.currentTrackId)
                    audioServiceHandler.onPlayerEvents(PlayerEvent.SeekToNext)
                }
            }

            is AudioPlayerContract.Event.OnChangeProgress -> {
                viewModelScope.launch {
                    audioServiceHandler.onPlayerEvents(
                        PlayerEvent.SeekTo,
                        seekPosition = ((duration * event.newProgress) / 100f).toLong()
                    )
                }
            }

            is AudioPlayerContract.Event.OnPlayPauseClicked -> {
                viewModelScope.launch {
                    audioServiceHandler.onPlayerEvents(PlayerEvent.PlayPause)
                }
            }

            is AudioPlayerContract.Event.OnPreviousClicked -> {
                viewModelScope.launch {
                    preloadPreviousTrack(event.currentTrackId)

                    audioServiceHandler.onPlayerEvents(PlayerEvent.SeekToPrevious)
                }
            }
        }
    }
}