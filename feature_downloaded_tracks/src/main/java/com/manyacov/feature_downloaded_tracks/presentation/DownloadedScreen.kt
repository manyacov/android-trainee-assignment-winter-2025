package com.manyacov.feature_downloaded_tracks.presentation

import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.manyacov.resources.theme.AvitoPlayerTheme
import com.manyacov.ui_kit.components.SearchPlaylist

@Composable()
fun DownloadedScreen(
    modifier: Modifier = Modifier,
    viewModel: DownloadedViewModel? = null
) {

    DownloadedScreen(modifier = Modifier.statusBarsPadding().systemBarsPadding())
}

@Composable
internal fun DownloadedScreen(
    modifier: Modifier = Modifier,
) {
    SearchPlaylist(
        modifier = modifier,
        trackList = listOf()
    )
}

@Preview
@Composable
fun DownloadedScreenPreview() {
    AvitoPlayerTheme {
        DownloadedScreen()
    }
}
