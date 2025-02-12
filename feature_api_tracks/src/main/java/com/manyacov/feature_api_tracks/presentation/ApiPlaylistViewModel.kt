package com.manyacov.feature_api_tracks.presentation

import androidx.lifecycle.viewModelScope
import com.manyacov.common.presentation.BaseViewModel
import com.manyacov.domain.avito_player.use_case.SearchApiTracksUseCase
import com.manyacov.domain.avito_player.use_case.GetApiTracksUseCase
import com.manyacov.domain.avito_player.utils.UiResult
import com.manyacov.feature_api_tracks.presentation.mapper.toTrackItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApiPlaylistViewModel @Inject constructor(
    private val getApiTracksUseCase: GetApiTracksUseCase,
    private val searchApiTracksUseCase: SearchApiTracksUseCase
) : BaseViewModel<ApiPlaylistContract.Event, ApiPlaylistContract.State, ApiPlaylistContract.Effect>() {

    override fun createInitialState() = ApiPlaylistContract.State()

    init {
        loadTracks()
    }

    private fun loadTracks() = viewModelScope.launch(Dispatchers.IO) {
        when(val result = getApiTracksUseCase.invoke(GetApiTracksUseCase.Params)) {
            is UiResult.Success -> {
                setState { copy(isLoading = false, playlist = result.data?.map { it.toTrackItem() }, issues = null) }
            }
            is UiResult.Error -> {
                setState { copy(isLoading = false, issues = result.issueType) }
            }
            else -> {}
        }
    }

    private fun searchTracks() = viewModelScope.launch(Dispatchers.IO) {
        setState { copy(isLoading = true) }
        if (uiState.value.searchString.isNotBlank()) {
            when(val result = searchApiTracksUseCase.invoke(SearchApiTracksUseCase.Params(uiState.value.searchString))) {
                is UiResult.Success -> {
                    setState { copy(isLoading = false, playlist = result.data?.map { it.toTrackItem() }, issues = null) }
                }
                is UiResult.Error -> {
                    setState { copy(isLoading = false, issues = result.issueType) }
                }
                else -> {}
            }
        } else {
            loadTracks()
        }
    }

    override fun handleEvent(event: ApiPlaylistContract.Event) {
        when (event) {
            is ApiPlaylistContract.Event.OnReloadClicked -> {
                if (uiState.value.searchString.isNotBlank()) {
                    searchTracks()
                } else {
                    loadTracks()
                }
            }
            is ApiPlaylistContract.Event.OnSearchClicked -> searchTracks()
            is ApiPlaylistContract.Event.UpdateSearchText -> setState { copy(searchString = event.searchText) }
        }
    }
}