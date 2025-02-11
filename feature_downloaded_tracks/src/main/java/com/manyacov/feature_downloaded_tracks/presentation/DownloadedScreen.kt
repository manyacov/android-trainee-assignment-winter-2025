package com.manyacov.feature_downloaded_tracks.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.manyacov.resources.theme.AvitoPlayerTheme
import com.manyacov.ui_kit.components.SearchPlaylist

@Composable()
fun DownloadedScreen(
    modifier: Modifier = Modifier,
    viewModel: DownloadedViewModel
) {

    DownloadedScreen(modifier = modifier)
}

@Composable
internal fun DownloadedScreen(
    modifier: Modifier = Modifier,
) {

    Box() {
        SearchPlaylist(trackList = listOf())
    }

}

@Preview
@Composable
fun DownloadedScreenPreview() {
    AvitoPlayerTheme {
        DownloadedScreen()
    }
}
