package com.manyacov.feature_audio_player.presentation.utils

import android.annotation.SuppressLint

@SuppressLint("DefaultLocale")
fun formatTime(seconds: Long): String {
    val minutes = seconds / 60
    val secs = seconds % 60
    return String.format("%02d:%02d", minutes, secs)
}