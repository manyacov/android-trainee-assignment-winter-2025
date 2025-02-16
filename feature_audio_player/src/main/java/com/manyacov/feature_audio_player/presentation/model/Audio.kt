package com.manyacov.feature_audio_player.presentation.model

import android.net.Uri
import android.os.Parcelable
import androidx.core.net.toUri
import kotlinx.parcelize.Parcelize

@Parcelize
data class Audio(
    val uri: Uri,
    val displayName: String,
    val id: Long,
    val artist: String,
    val title: String,
    val imageUrl: String
): Parcelable

internal val audioDummy = Audio("".toUri(), "", 0L, "", "", "")