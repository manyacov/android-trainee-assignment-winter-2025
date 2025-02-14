package com.manyacov.ui_kit.list_items

import android.graphics.Bitmap

data class TrackItem(
    val id: String,
    val title: String,
    val artistName: String,
    val imageUrl: String? = null,
    val albumArtBitmap: Bitmap? = null
)
