package com.manyacov.feature_api_tracks.presentation

import androidx.lifecycle.viewModelScope
import com.manyacov.common.presentation.BaseViewModel
import com.manyacov.domain.avito_player.use_case.GetApiTracksUseCase
import com.manyacov.feature_api_tracks.presentation.mapper.toTrackItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ApiPlaylistViewModel @Inject constructor(
    private val getApiTracksUseCase: GetApiTracksUseCase
): BaseViewModel<ApiPlaylistContract.Event, ApiPlaylistContract.State, ApiPlaylistContract.Effect>() {

//    private val searchText = MutableStateFlow("")
//    private val tracks = combine(
//        searchText.debounce(Constants.SEARCH_DEBOUNCE_MILLS),
//        searchFilter
//    ) { searchText, searchFilter ->
//        searchText to searchFilter
//    }.flatMapLatest { (searchText, searchFilter) ->
//        flow { emit(musicManager.searchSongs(searchText, searchFilter).cachedIn(viewModelScope)) }
//    }

    override fun createInitialState() = ApiPlaylistContract.State()

    init {
       loadTracks()
    }

    private fun loadTracks() = viewModelScope.launch(Dispatchers.IO) {
        val list = getApiTracksUseCase.invoke(GetApiTracksUseCase.Params)
        setState { copy(isLoading = false, playlist = list.map { it.toTrackItem() }) }
    }

    override fun handleEvent(event: ApiPlaylistContract.Event) {

    }
}