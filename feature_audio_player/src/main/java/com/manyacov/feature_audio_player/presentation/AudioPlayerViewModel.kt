package com.manyacov.feature_audio_player.presentation

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.core.net.toUri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.saveable
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import com.manyacov.common.presentation.BaseViewModel
import com.manyacov.domain.avito_player.use_case.GetApiTrackUseCase
import com.manyacov.domain.avito_player.use_case.GetSessionUseCase
import com.manyacov.feature_audio_player.notification_service.service.AvitoAudioServiceHandler
import com.manyacov.feature_audio_player.notification_service.service.AvitoAudioState
import com.manyacov.feature_audio_player.notification_service.service.PlayerEvent
import com.manyacov.feature_audio_player.presentation.model.Audio
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

private val audioDummy = Audio(
    "".toUri(), "", 0L, "", 0, "", ""
)

@HiltViewModel
class AudioPlayerViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val audioServiceHandler: AvitoAudioServiceHandler,
    private val getSessionUseCase: GetSessionUseCase,
    private val getApiTrackUseCase: GetApiTrackUseCase,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<AudioPlayerContract.Event, AudioPlayerContract.State, AudioPlayerContract.Effect>() {

    override fun createInitialState() = AudioPlayerContract.State()

    var duration by savedStateHandle.saveable { mutableStateOf(0L) }
    var progress by savedStateHandle.saveable { mutableStateOf(0f) }
    var progressString by savedStateHandle.saveable { mutableStateOf("00:00") }
    var isPlaying by savedStateHandle.saveable { mutableStateOf(false) }
    var currentSelectedAudio by savedStateHandle.saveable { mutableStateOf(audioDummy) }
    var audioList by savedStateHandle.saveable { mutableStateOf(listOf<Audio>()) }

    init {
        viewModelScope.launch {
            audioServiceHandler.audioState.collectLatest { mediaState ->
                when (mediaState) {
                    is AvitoAudioState.Initial -> setState { copy(isLoading = true) }
                    is AvitoAudioState.Buffering -> calculateProgressValue(mediaState.progress)
                    is AvitoAudioState.Playing -> isPlaying = mediaState.isPlaying
                    is AvitoAudioState.Progress -> calculateProgressValue(mediaState.progress)
                    is AvitoAudioState.CurrentPlaying -> {
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

    private fun setMediaItems() {
        audioList.map { audio ->
            MediaItem.Builder()
                .setUri(audio.uri)
                .setMediaMetadata(
                    MediaMetadata.Builder()
                        .setAlbumArtist(audio.artist)
                        .setDisplayTitle(audio.title)
                        .setSubtitle(audio.displayName)
                        .build()
                )
                .build()
        }.also {
            audioServiceHandler.setMediaItemList(it)
        }
    }

    private fun calculateProgressValue(currentProgress: Long) {
        progress =
            if (currentProgress > 0) ((currentProgress.toFloat() / duration.toFloat()) * 100f)
            else 0f
        progressString = formatDuration(currentProgress)
    }

    @SuppressLint("DefaultLocale")
    fun formatDuration(duration: Long): String {
        val minute = TimeUnit.MINUTES.convert(duration, TimeUnit.MILLISECONDS)
        val seconds = (minute) - minute * TimeUnit.SECONDS.convert(1, TimeUnit.MINUTES)
        return String.format("%02d:%02d", minute, seconds)
    }

    override fun onCleared() {
        viewModelScope.launch {
            audioServiceHandler.onPlayerEvents(PlayerEvent.Stop)
        }
        super.onCleared()
    }

    private fun loadLocal(path: String?) = viewModelScope.launch {
        val audio = getAudioFromPath(
            contentResolver = context.contentResolver,
            filePath = path.orEmpty()
        )

        audio?.let {
            audioList = listOf(audio)
            setMediaItems()
        }
    }

    private fun loadTrack() = viewModelScope.launch {

        getSessionUseCase.invoke(GetSessionUseCase.Params).collect { info ->
            if (info.second == true) {
                loadLocal(info.first)
            } else {
                loadApiTrack(info.first)
            }
        }
    }

    private suspend fun loadApiTrack(id: String?) {
        val audioDomain =
            getApiTrackUseCase.invoke(GetApiTrackUseCase.Params(id.orEmpty())).data

        val audio = audioDomain?.let {
            Audio(
                uri = it.uri,
                displayName = audioDomain.displayName,
                id = audioDomain.id,
                artist = audioDomain.artist,
                duration = audioDomain.duration,
                title = audioDomain.title,
                imageUrl = audioDomain.imageUrl,
            )
        }

        audio?.let {
            audioList = listOf(audio)
            setMediaItems()
        }
    }


    override fun handleEvent(event: AudioPlayerContract.Event) {
        when (event) {
            is AudioPlayerContract.Event.OnScreenOpened -> loadTrack()

            is AudioPlayerContract.Event.OnNextClicked -> {}

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

            is AudioPlayerContract.Event.OnPreviousClicked -> TODO()
        }
    }
}