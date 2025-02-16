package com.manyacov.feature_audio_player.presentation.local

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.saveable
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import com.manyacov.common.presentation.BaseViewModel
import com.manyacov.domain.avito_player.use_case.GetCurrentTrackListUseCase
import com.manyacov.domain.avito_player.use_case.GetSessionUseCase
import com.manyacov.feature_audio_player.notification_service.service.AvitoAudioServiceHandler
import com.manyacov.feature_audio_player.notification_service.service.AvitoAudioState
import com.manyacov.feature_audio_player.notification_service.service.PlayerEvent
import com.manyacov.feature_audio_player.presentation.AudioPlayerContract
import com.manyacov.feature_audio_player.presentation.utils.getAudioFromPath
import com.manyacov.feature_audio_player.presentation.model.Audio
import com.manyacov.feature_audio_player.presentation.model.PlayerTime
import com.manyacov.feature_audio_player.presentation.model.audioDummy
import com.manyacov.feature_audio_player.presentation.utils.formatTime
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocalAudioPlayerViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val audioServiceHandler: AvitoAudioServiceHandler,
    private val getSessionUseCase: GetSessionUseCase,
    private val getCurrentTrackListUseCase: GetCurrentTrackListUseCase,
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
            MediaItem.Builder()
                .setUri(audio.uri)
                .setMediaId(audio.uri.toString())
                .setMediaMetadata(
                    MediaMetadata.Builder()
                        .setAlbumArtist(audio.artist)
                        .setDisplayTitle(audio.title)
                        .setSubtitle(audio.displayName)
                        .build()
                )
                .build()
        }.also {
            audioServiceHandler.setMediaItemList(it, currentPath)
        }
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

    private fun loadLocal(path: String?) = viewModelScope.launch {
        getCurrentTrackListUseCase.invoke(GetCurrentTrackListUseCase.Params).collect { paths ->
            val audios = paths.mapNotNull {
                getAudioFromPath(
                    contentResolver = context.contentResolver,
                    filePath = it
                )
            }

            audioList.addAll(audios)
            setMediaItems(path ?: "")
        }
    }

    private fun loadTrack() = viewModelScope.launch(Dispatchers.IO) {
        getSessionUseCase.invoke(GetSessionUseCase.Params).collect { info ->
            loadLocal(info.orEmpty())
        }
    }

    override fun handleEvent(event: AudioPlayerContract.Event) {
        when (event) {
            is AudioPlayerContract.Event.OnScreenOpened -> loadTrack()

            is AudioPlayerContract.Event.OnNextClicked -> {
                viewModelScope.launch {
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
                    audioServiceHandler.onPlayerEvents(PlayerEvent.SeekToPrevious)
                }
            }
        }
    }
}