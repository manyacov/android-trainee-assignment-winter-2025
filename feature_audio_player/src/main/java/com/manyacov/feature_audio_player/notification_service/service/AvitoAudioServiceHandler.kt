package com.manyacov.feature_audio_player.notification_service.service

import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.Tracks
import androidx.media3.exoplayer.ExoPlayer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AvitoAudioServiceHandler @Inject constructor(
    private val exoPlayer: ExoPlayer,
) : Player.Listener {
    private val _audioState: MutableStateFlow<AvitoAudioState> = MutableStateFlow(AvitoAudioState.Initial)
    val audioState: StateFlow<AvitoAudioState> = _audioState.asStateFlow()

    private var job: Job? = null

    init {
        exoPlayer.addListener(this)
    }

    fun addNextMediaItem(index: Int, mediaItem: MediaItem) {
        exoPlayer.addMediaItem(index, mediaItem)
        exoPlayer.removeMediaItem(0)
    }

    fun addPreviousMediaItem(mediaItem: MediaItem) {
        exoPlayer.addMediaItem(0, mediaItem)
        exoPlayer.removeMediaItem(5)
    }

    fun setMediaItemList(mediaItems: List<MediaItem>, id: String) {
        exoPlayer.setMediaItems(mediaItems)
        exoPlayer.seekTo(mediaItems.indexOfFirst { it.mediaId == id }, 0)
        exoPlayer.prepare()
        exoPlayer.playWhenReady = true
    }

    suspend fun onPlayerEvents(
        playerEvent: PlayerEvent,
        selectedAudioIndex: Int = -1,
        seekPosition: Long = 0,
    ) {
        when (playerEvent) {
            PlayerEvent.Backward -> exoPlayer.seekBack()
            PlayerEvent.Forward -> exoPlayer.seekForward()

            PlayerEvent.SeekToNext -> exoPlayer.seekToNextMediaItem()
            PlayerEvent.SeekToPrevious -> exoPlayer.seekToPreviousMediaItem()

            PlayerEvent.PlayPause -> playOrPause()

            PlayerEvent.SeekTo -> exoPlayer.seekTo(seekPosition)
            PlayerEvent.SelectedAudioChange -> {
                when (selectedAudioIndex) {
                    exoPlayer.currentMediaItemIndex -> {
                        playOrPause()
                    }

                    else -> {
                        exoPlayer.seekToDefaultPosition(selectedAudioIndex)
                        _audioState.value = AvitoAudioState.Playing(
                            isPlaying = true
                        )
                        exoPlayer.playWhenReady = true
                        startProgressUpdate()
                    }
                }
            }

            PlayerEvent.Stop -> stopProgressUpdate()
            is PlayerEvent.UpdateProgress -> {
                exoPlayer.seekTo(
                    (exoPlayer.duration * playerEvent.newProgress).toLong()
                )
            }
        }
    }

    override fun onPlaybackStateChanged(playbackState: Int) {
        when (playbackState) {
            ExoPlayer.STATE_BUFFERING -> _audioState.value =
                AvitoAudioState.Buffering(exoPlayer.currentPosition)

            ExoPlayer.STATE_READY -> _audioState.value =
                AvitoAudioState.Ready(exoPlayer.duration)
        }
    }

    override fun onIsPlayingChanged(isPlaying: Boolean) {
        _audioState.value = AvitoAudioState.Playing(isPlaying = isPlaying)
        _audioState.value = AvitoAudioState.CurrentPlaying(exoPlayer.currentMediaItemIndex)

        if (isPlaying) {
            GlobalScope.launch(Dispatchers.Main) {
                startProgressUpdate()
            }
        } else {
            stopProgressUpdate()
        }
    }

    override fun onTracksChanged(tracks: Tracks) {
        _audioState.value = AvitoAudioState.CurrentPlaying(exoPlayer.currentMediaItemIndex)
    }

    private suspend fun playOrPause() {
        if (exoPlayer.isPlaying) {
            exoPlayer.pause()
            stopProgressUpdate()

        } else {
            exoPlayer.play()
            _audioState.value = AvitoAudioState.Playing(isPlaying = true)

            startProgressUpdate()
        }
    }

    private suspend fun startProgressUpdate() = job.run {
        while (true) {
            delay(1000)
            _audioState.value = AvitoAudioState.Progress(exoPlayer.currentPosition)
        }
    }

    private fun stopProgressUpdate() {
        job?.cancel()
        _audioState.value = AvitoAudioState.Playing(isPlaying = false)
    }
}

sealed class PlayerEvent {
    data object PlayPause : PlayerEvent()
    data object SelectedAudioChange : PlayerEvent()
    data object Backward : PlayerEvent()
    data object SeekToNext : PlayerEvent()
    data object SeekToPrevious : PlayerEvent()
    data object Forward : PlayerEvent()
    data object SeekTo : PlayerEvent()
    data object Stop : PlayerEvent()
    data class UpdateProgress(val newProgress: Float) : PlayerEvent()
}

sealed class AvitoAudioState {
    data object Initial : AvitoAudioState()
    data class Ready(val duration: Long) : AvitoAudioState()
    data class Progress(val progress: Long) : AvitoAudioState()
    data class Buffering(val progress: Long) : AvitoAudioState()
    data class Playing(val isPlaying: Boolean) : AvitoAudioState()
    data class CurrentPlaying(val mediaItemIndex: Int) : AvitoAudioState()
}