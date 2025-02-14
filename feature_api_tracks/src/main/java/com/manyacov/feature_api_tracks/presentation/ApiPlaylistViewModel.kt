package com.manyacov.feature_api_tracks.presentation

import androidx.lifecycle.viewModelScope
import com.manyacov.common.presentation.BaseViewModel
import com.manyacov.domain.avito_player.use_case.SearchApiTracksUseCase
import com.manyacov.domain.avito_player.use_case.GetApiTracksUseCase
import com.manyacov.domain.avito_player.utils.UiResult
import com.manyacov.feature_api_tracks.presentation.mapper.toTrackItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.manyacov.common.Constants.PAGE_SIZE
import com.manyacov.common.Constants.PREFETCH_DISTANCE
import com.manyacov.common.Constants.SEARCH_DEBOUNCE_MILLS
import com.manyacov.domain.avito_player.model.PlaylistTrack
import com.manyacov.domain.avito_player.use_case.SearchTrackFlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn

@HiltViewModel
class ApiPlaylistViewModel @Inject constructor(
    private val getApiTracksUseCase: GetApiTracksUseCase,
    private val searchApiTracksUseCase: SearchApiTracksUseCase,
    private val searchTrackFlowUseCase: SearchTrackFlowUseCase
) : BaseViewModel<ApiPlaylistContract.Event, ApiPlaylistContract.State, ApiPlaylistContract.Effect>() {

    override fun createInitialState() = ApiPlaylistContract.State()

    private val searchText = MutableStateFlow("")

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val songs = searchText
        .debounce(SEARCH_DEBOUNCE_MILLS.milliseconds)
        .flatMapLatest { searchText ->
            flow { emit(searchSongs(searchText).cachedIn(viewModelScope)) }
        }

    private fun searchSongs(searchText: String): Flow<PagingData<PlaylistTrack>> {
        return searchTrackFlowUseCase.invoke(
            SearchTrackFlowUseCase.Params(searchText)
        )
    }

    init {
        //loadTracks()

        combine(searchText, songs) { searchText, songs ->
            setState { copy(searchString = searchText, songs = songs) }
        }.launchIn(viewModelScope)
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

//    private fun searchTracks() = viewModelScope.launch(Dispatchers.IO) {
//        setState { copy(isLoading = true) }
//        if (uiState.value.searchString.isNotBlank()) {
//            when(val result = searchApiTracksUseCase.invoke(SearchApiTracksUseCase.Params(uiState.value.searchString))) {
//                is UiResult.Success -> {
//                    setState { copy(isLoading = false, playlist = result.data?.map { it.toTrackItem() }, issues = null) }
//                }
//                is UiResult.Error -> {
//                    setState { copy(isLoading = false, issues = result.issueType) }
//                }
//                else -> {}
//            }
//        } else {
//            loadTracks()
//        }
//    }

    override fun handleEvent(event: ApiPlaylistContract.Event) {
        when (event) {
            is ApiPlaylistContract.Event.OnReloadClicked -> {
                if (uiState.value.searchString.isNotBlank()) {
                    //searchTracks()
                } else {
                    loadTracks()
                }
            }
            is ApiPlaylistContract.Event.UpdateSearchText -> searchText.value = event.searchText
        }
    }
}