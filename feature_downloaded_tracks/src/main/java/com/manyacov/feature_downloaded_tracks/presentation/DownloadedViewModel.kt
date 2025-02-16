package com.manyacov.feature_downloaded_tracks.presentation

import android.content.Context
import android.provider.MediaStore
import androidx.lifecycle.viewModelScope
import com.manyacov.common.presentation.BaseViewModel
import com.manyacov.domain.avito_player.use_case.SaveCurrentTrackListUseCase
import com.manyacov.domain.avito_player.use_case.SaveSessionUseCase
import com.manyacov.feature_downloaded_tracks.presentation.mapper.convertToTrackItem
import com.manyacov.ui_kit.list_items.TrackItem
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DownloadedViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val saveSessionUseCase: SaveSessionUseCase,
    private val saveCurrentTrackListUseCase: SaveCurrentTrackListUseCase
) : BaseViewModel<DownloadedPlaylistContract.Event, DownloadedPlaylistContract.State, DownloadedPlaylistContract.Effect>() {

    override fun createInitialState() = DownloadedPlaylistContract.State()

    private val searchText = MutableStateFlow("")
    private val fullPlaylist = mutableListOf<TrackItem>()

    private val audioFilesPaths = mutableSetOf<String>()

    init {
        searchSong()
    }

    private fun searchSong() = viewModelScope.launch {
        searchText.collectLatest { searchText ->
            setState {
                copy(searchString = searchText, playlist = if (searchString.isBlank()) {
                    fullPlaylist
                } else {
                    fullPlaylist.filter {
                        it.title.lowercase().contains(searchText.lowercase())
                    }
                })
            }
        }
    }

    private fun loadTracks() = viewModelScope.launch(Dispatchers.IO) {
        setState { copy(isLoading = true) }

        val projection = arrayOf(MediaStore.Audio.Media.DATA)
        val cursor = context.contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            null
        )

        cursor?.use {
            val dataIndex = it.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)
            while (it.moveToNext()) {
                val path = it.getString(dataIndex)
                audioFilesPaths.add(path)
            }
        }

        val result = audioFilesPaths.mapNotNull { convertToTrackItem(it) }
        fullPlaylist.addAll(result)

        setState { copy(isLoading = false, playlist = result, isPermissionsRejected = false) }
    }

    private fun searchTracks() = viewModelScope.launch(Dispatchers.IO) {
        setState { copy(isLoading = true) }
    }

    override fun handleEvent(event: DownloadedPlaylistContract.Event) {
        when (event) {
            is DownloadedPlaylistContract.Event.OnReloadClicked -> loadTracks()
            is DownloadedPlaylistContract.Event.OnSearchClicked -> searchTracks()
            is DownloadedPlaylistContract.Event.OnRejectedPermissions -> setState {
                copy(
                    isLoading = false,
                    isPermissionsRejected = true
                )
            }

            is DownloadedPlaylistContract.Event.UpdateSearchText -> searchText.value = event.searchText
            is DownloadedPlaylistContract.Event.OnTrackClicked -> { savePath(event.filePath) }
        }
    }

    private fun savePath(filePath: String) = viewModelScope.launch(Dispatchers.IO) {
        saveSessionUseCase.invoke(SaveSessionUseCase.Params(filePath))
        saveCurrentTrackListUseCase.invoke(SaveCurrentTrackListUseCase.Params(audioFilesPaths.toList()))
    }
}