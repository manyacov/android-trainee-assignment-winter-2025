package com.manyacov.feature_api_tracks.presentation

import com.manyacov.common.presentation.UiEffect
import com.manyacov.common.presentation.UiEvent
import com.manyacov.common.presentation.UiState
import com.manyacov.ui_kit.list_items.TrackItem

class ApiPlaylistContract {
    sealed class Event : UiEvent {
        data object OnReloadClicked : Event()

        data object OnSearchClicked : Event()
        data class UpdateSearchText(val searchText: String) : Event()

    }

    data class State(
        val isLoading: Boolean = true,
        val playlist: List<TrackItem> = listOf(),
        val searchString: String = ""
    ) : UiState

    sealed class Effect : UiEffect
}