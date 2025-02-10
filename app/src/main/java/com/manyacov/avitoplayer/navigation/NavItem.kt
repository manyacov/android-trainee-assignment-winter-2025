package com.manyacov.avitoplayer.navigation

import com.manyacov.resources.R

sealed class NavItem {
    object Downloaded :
        BottomNavItem(
            path = NavPath.DOWNLOADED,
            titleRes = R.string.downloaded_tracks,
            iconRes = R.drawable.ic_downloaded
        )

    object Online :
        BottomNavItem(
            path = NavPath.ONLINE,
            titleRes = R.string.api_tracks,
            iconRes = R.drawable.ic_online
        )

    object Song :
        BottomNavItem(
            path = NavPath.SONG,
            titleRes = R.string.track,
            iconRes = 0
        )
}