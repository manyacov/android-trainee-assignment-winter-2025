package com.manyacov.feature_audio_player.presentation.utils

import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import com.manyacov.feature_audio_player.presentation.model.Audio

fun Audio.toMediaItem(): MediaItem {
    return MediaItem.Builder()
        .setUri(this.uri)
        .setMediaId(this.id.toString())
        .setMediaMetadata(
            MediaMetadata.Builder()
                .setAlbumArtist(this.artist)
                .setDisplayTitle(this.title)
                .setSubtitle(this.displayName)
                .build()
        )
        .build()
}