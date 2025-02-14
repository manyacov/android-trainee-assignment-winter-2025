package com.manyacov.domain.avito_player.model

import android.net.Uri

data class AudioDomain(
    val uri: Uri,
    val displayName: String,
    val id: Long,
    val artist: String,
    val duration: Int,
    val title: String,
    val imageUrl: String
)