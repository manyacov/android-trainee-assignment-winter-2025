package com.manyacov.feature_api_tracks.presentation

import androidx.paging.PagingData
import com.manyacov.common.presentation.UiEffect
import com.manyacov.common.presentation.UiEvent
import com.manyacov.common.presentation.UiState
import com.manyacov.domain.avito_player.model.PlaylistTrack
import com.manyacov.domain.avito_player.utils.UiIssues
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class ApiPlaylistContract {
    sealed class Event : UiEvent {
        data object OnReloadClicked : Event()
        data class UpdateSearchText(val searchText: String) : Event()
        data class OnTrackClicked(val trackId: String) : Event()
    }

    data class State(
        val isLoading: Boolean = true,
        val playlist: Flow<PagingData<PlaylistTrack>> = emptyFlow(),
        val searchString: String = "",
        val issues: UiIssues? = null
    ) : UiState

    sealed class Effect : UiEffect
}