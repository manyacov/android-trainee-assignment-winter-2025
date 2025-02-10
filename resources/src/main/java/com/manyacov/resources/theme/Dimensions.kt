package com.manyacov.resources.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val LocalDim = compositionLocalOf { Dimensions() }

data class Dimensions(
    val spaceSize4: Dp = 4.dp,
    val spaceSize14: Dp = 14.dp,
    val spaceSize16: Dp = 16.dp,
    val spaceSize24: Dp = 24.dp,
)