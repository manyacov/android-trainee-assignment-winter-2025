package com.manyacov.feature_downloaded_tracks.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.manyacov.resources.theme.AvitoPlayerTheme
import details.AppSearchBar

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

    Column(
        modifier = modifier
            .fillMaxSize()
        //.padding(LocalDim.current.spaceSize14)
    ) {
        AppSearchBar()
    }

}

@Preview
@Composable
fun DownloadedScreenPreview() {
    AvitoPlayerTheme {
        DownloadedScreen()
    }
}
