package com.manyacov.resources.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

val LocalTextDim = compositionLocalOf { TextDimensions() }

data class TextDimensions(
    val textSize12: TextUnit = 12.sp,
    val textSize16: TextUnit  = 16.sp,
    val textSize18: TextUnit  = 18.sp,
)