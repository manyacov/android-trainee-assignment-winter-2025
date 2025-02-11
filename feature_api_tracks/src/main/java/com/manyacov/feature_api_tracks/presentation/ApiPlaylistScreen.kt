package com.manyacov.feature_api_tracks.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.manyacov.resources.theme.AvitoPlayerTheme
import com.manyacov.ui_kit.components.SearchPlaylist

@Composable()
fun ApiPlaylistScreen(
    modifier: Modifier = Modifier,
    viewModel: ApiPlaylistViewModel
) {

    ApiPlaylistScreen(modifier = modifier)
}

@Composable
internal fun ApiPlaylistScreen(
    modifier: Modifier = Modifier,
) {

    Box() {
        SearchPlaylist(trackList = listOf())
    }

}

@Preview
@Composable
fun ApiPlaylistScreenPreview() {
    AvitoPlayerTheme {
        ApiPlaylistScreen()
    }
}
