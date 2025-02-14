package com.manyacov.feature_downloaded_tracks.presentation

import com.manyacov.common.presentation.UiEffect
import com.manyacov.common.presentation.UiEvent
import com.manyacov.common.presentation.UiState
import com.manyacov.ui_kit.list_items.TrackItem

class DownloadedPlaylistContract {
    sealed class Event : UiEvent {
        data object OnRejectedPermissions : Event()
        data object OnReloadClicked : Event()
        data object OnSearchClicked : Event()
        data class UpdateSearchText(val searchText: String) : Event()
        data class OnTrackClicked(val filePath: String) : Event()
    }

    data class State(
        val isLoading: Boolean = true,
        val playlist: List<TrackItem> = listOf(),
        val searchString: String = "",
        val requestPermission: Boolean = false,
        val isPermissionsRejected: Boolean = true
    ) : UiState

    sealed class Effect : UiEffect
}