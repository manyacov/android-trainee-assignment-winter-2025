package com.manyacov.feature_audio_player.notification_service.service

import android.content.Intent
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSession.ControllerInfo
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import androidx.media3.session.MediaSessionService
import com.manyacov.feature_audio_player.notification_service.notification.AvitoAudioNotificationManager

@AndroidEntryPoint
class AvitoPlayerService : MediaSessionService() {

    @Inject
    lateinit var mediaSession: MediaSession

    @Inject
    lateinit var notificationManager: AvitoAudioNotificationManager

    @UnstableApi
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        notificationManager.startNotificationService(
            mediaSession = mediaSession,
            mediaSessionService = this
        )
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onGetSession(controllerInfo: ControllerInfo): MediaSession =
        mediaSession

    override fun onDestroy() {
        super.onDestroy()
        mediaSession.apply {
            release()
            if (player.playbackState != Player.STATE_IDLE) {
                player.seekTo(0)
                player.playWhenReady = false
                player.stop()
            }
        }
    }
}