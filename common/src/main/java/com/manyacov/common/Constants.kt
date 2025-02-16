package com.manyacov.common

object Constants {
    const val SCREEN_CHANGING_DURATION = 500
    const val BOTTOM_NAV_CHANGING_DURATION = 250L

    const val INITIAL_INDEX = 0
    const val INITIAL_PAGE_SIZE = 20
    const val PAGE_SIZE = 20
    const val PREFETCH_DISTANCE = 20

    const val SEARCH_DEBOUNCE_MILLS = 200
}

object NavPath {
    const val DOWNLOADED = "downloaded"
    const val ONLINE = "online"
    const val LOCAL_PLAYER = "local_player"
    const val API_PLAYER = "api_player"
}