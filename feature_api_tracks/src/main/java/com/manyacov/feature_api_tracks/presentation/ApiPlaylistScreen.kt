package com.manyacov.feature_api_tracks.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.manyacov.resources.theme.AvitoPlayerTheme
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
        playlist = state.playlist
    )
}

@Composable
internal fun ApiPlaylistScreen(
    modifier: Modifier = Modifier,
    playlist: List<TrackItem>
) {

    Box() {
        SearchPlaylist(
            trackList = playlist
        )
    }

}

@Preview
@Composable
fun ApiPlaylistScreenPreview() {
    AvitoPlayerTheme {
        ApiPlaylistScreen(playlist = listOf())
    }
}
