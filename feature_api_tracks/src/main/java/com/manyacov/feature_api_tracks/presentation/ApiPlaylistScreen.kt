package com.manyacov.feature_api_tracks.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.manyacov.resources.theme.AvitoPlayerTheme
import com.manyacov.resources.theme.LocalDim
import com.manyacov.ui_kit.components.SearchPlaylist
import com.manyacov.ui_kit.list_items.TrackItem

@Composable()
fun ApiPlaylistScreen(
    modifier: Modifier = Modifier,
    viewModel: ApiPlaylistViewModel
) {
    val state by viewModel.uiState.collectAsState()

    ApiPlaylistScreen(
        modifier = modifier,
        playlist = state.playlist,
        searchString = state.searchString,
        onReloadClicked = { viewModel.setEvent(ApiPlaylistContract.Event.OnReloadClicked) },
        onSearchValueChange = { viewModel.setEvent(ApiPlaylistContract.Event.UpdateSearchText(it)) }
    )
}

@Composable
internal fun ApiPlaylistScreen(
    modifier: Modifier = Modifier,
    playlist: List<TrackItem>,
    searchString: String = "",
    onReloadClicked: () -> Unit = {},
    onSearchValueChange: (String) -> Unit = {}
) {

    SearchPlaylist(
        modifier = modifier.statusBarsPadding().systemBarsPadding().padding(LocalDim.current.spaceSize16),
        trackList = playlist,
        onReloadClicked = onReloadClicked,
        searchString = searchString,
        onSearchValueChange = onSearchValueChange
    )
}

@Preview
@Composable
fun ApiPlaylistScreenPreview() {
    AvitoPlayerTheme {
        ApiPlaylistScreen(playlist = listOf())
    }
}
