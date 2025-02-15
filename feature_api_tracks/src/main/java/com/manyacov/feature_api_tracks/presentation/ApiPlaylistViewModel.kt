package com.manyacov.feature_api_tracks.presentation

import androidx.lifecycle.viewModelScope
import com.manyacov.common.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.manyacov.common.Constants.SEARCH_DEBOUNCE_MILLS
import com.manyacov.domain.avito_player.model.PlaylistTrack
import com.manyacov.domain.avito_player.use_case.SaveCurrentTrackList
import com.manyacov.domain.avito_player.use_case.SaveSessionUseCase
import com.manyacov.domain.avito_player.use_case.SearchTrackFlowUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch

@HiltViewModel
class ApiPlaylistViewModel @Inject constructor(
    private val searchTrackFlowUseCase: SearchTrackFlowUseCase,
    private val saveSessionUseCase: SaveSessionUseCase,
    private val saveCurrentTrackList: SaveCurrentTrackList,
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
        combine(searchText, songs) { searchText, tracks ->
            setState { copy(searchString = searchText, playlist = tracks) }
        }.launchIn(viewModelScope)
    }

    override fun handleEvent(event: ApiPlaylistContract.Event) {
        when (event) {
            is ApiPlaylistContract.Event.OnReloadClicked -> {} //TODO: change reload logic
            is ApiPlaylistContract.Event.UpdateSearchText -> searchText.value = event.searchText
            is ApiPlaylistContract.Event.OnTrackClicked -> { savePath(event.trackId, event.tracksIds) }
        }
    }

    private fun savePath(trackId: String, tracksIds: List<Long>) = viewModelScope.launch(Dispatchers.IO) {
        saveSessionUseCase.invoke(SaveSessionUseCase.Params(trackId, false))
        saveCurrentTrackList.invoke(SaveCurrentTrackList.Params(tracksIds.map { it.toString() }))
    }
}