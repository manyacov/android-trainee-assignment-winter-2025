package com.manyacov.avitoplayer.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

abstract class BottomNavItem(
    val path: String,
    @StringRes val titleRes: Int,
    @DrawableRes val iconRes: Int
)