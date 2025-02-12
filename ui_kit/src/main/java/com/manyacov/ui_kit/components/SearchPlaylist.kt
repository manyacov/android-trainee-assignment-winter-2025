package com.manyacov.ui_kit.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.manyacov.resources.theme.AvitoPlayerTheme
import com.manyacov.resources.theme.LocalDim
import androidx.compose.foundation.lazy.items
import com.manyacov.ui_kit.details.AppSearchBar
import com.manyacov.ui_kit.list_items.PlaylistItem
import com.manyacov.ui_kit.list_items.TrackItem

@Composable
fun SearchPlaylist(
    modifier: Modifier = Modifier,
    trackList: List<TrackItem>
) {
    Column(
        modifier = modifier.fillMaxSize().statusBarsPadding().systemBarsPadding(),
        verticalArrangement = Arrangement.spacedBy(LocalDim.current.spaceSize4)
    ) {
        AppSearchBar()

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(LocalDim.current.spaceSize16)
        ) {
            items(trackList) { item ->
                PlaylistItem(trackItem = item)
            }
        }
    }
}

@Preview
@Composable
fun SearchPlaylistPreview() {
    val mockTrackList = listOf(
        TrackItem(
            id = "1",
            title = "Track One",
            artistName = "Artist A",
            imageUrl = "https://example.com/image1.jpg"
        ),
        TrackItem(
            id = "2",
            title = "Track Two",
            artistName = "Artist B",
            imageUrl = "https://example.com/image2.jpg"
        ),
        TrackItem(
            id = "3",
            title = "Track Three",
            artistName = "Artist C",
            imageUrl = "https://example.com/image3.jpg"
        ),
        TrackItem(
            id = "4",
            title = "Track Four",
            artistName = "Artist D",
            imageUrl = "https://example.com/image4.jpg"
        ),
        TrackItem(
            id = "5",
            title = "Track Five",
            artistName = "Artist E",
            imageUrl = null
        )
    )

    AvitoPlayerTheme {
        SearchPlaylist(trackList = mockTrackList)
    }
}