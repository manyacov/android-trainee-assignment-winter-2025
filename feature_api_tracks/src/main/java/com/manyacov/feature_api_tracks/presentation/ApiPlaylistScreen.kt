package com.manyacov.feature_api_tracks.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.manyacov.domain.avito_player.model.PlaylistTrack
import com.manyacov.feature_api_tracks.presentation.mapper.toStringDescription
import com.manyacov.feature_api_tracks.presentation.mapper.toTrackItem
import com.manyacov.feature_api_tracks.presentation.utils.isLoading
import com.manyacov.feature_api_tracks.presentation.utils.rememberLazyListState
import com.manyacov.resources.R
import com.manyacov.resources.theme.AvitoPlayerTheme
import com.manyacov.resources.theme.LocalDim
import com.manyacov.ui_kit.components.TextInfoView
import com.manyacov.ui_kit.details.AppSearchBar
import com.manyacov.ui_kit.list_items.PlaylistItem

@Composable
fun ApiPlaylistScreen(
    modifier: Modifier = Modifier,
    viewModel: ApiPlaylistViewModel
) {
    val state by viewModel.uiState.collectAsState()

    ApiPlaylistScreen(
        modifier = modifier,
        items = state.playlist.collectAsLazyPagingItems(),
        isError = state.issues != null,
        errorDescription = state.issues?.toStringDescription().orEmpty(),
        searchString = state.searchString,
        onReloadClicked = { viewModel.setEvent(ApiPlaylistContract.Event.OnReloadClicked) },
        onSearchValueChange = { viewModel.setEvent(ApiPlaylistContract.Event.UpdateSearchText(it)) }
    )
}

@Composable
internal fun ApiPlaylistScreen(
    modifier: Modifier = Modifier,
    items: LazyPagingItems<PlaylistTrack>,
    searchString: String = "",
    isError: Boolean = false,
    errorDescription: String = "",
    onReloadClicked: () -> Unit = {},
    onSearchValueChange: (String) -> Unit = {}
) {
    Column(
        modifier = modifier
            .padding(LocalDim.current.spaceSize16)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(LocalDim.current.spaceSize16)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(LocalDim.current.spaceSize16)
        ) {
            AppSearchBar(
                modifier = Modifier.weight(1f),
                value = searchString,
                onValueChange = { value -> onSearchValueChange(value) },
            )

            Icon(
                modifier = Modifier
                    .size(LocalDim.current.spaceSize42)
                    .clickable { onReloadClicked() },
                painter = painterResource(R.drawable.ic_reload),
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = "ic_reload_clickable",
            )
        }

        if (isError) {
            TextInfoView(
                modifier = Modifier.fillMaxSize(),
                info = errorDescription
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(),
                state = items.rememberLazyListState()
            ) {
                items(
                    count = items.itemCount,
                    key = items.itemKey(),
                    contentType = items.itemContentType()
                ) { index ->
                    val song = items[index]
                    song?.let {
                        PlaylistItem(trackItem = song.toTrackItem())
                    }
                }
                when {
                    items.loadState.isLoading -> {
                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                            }
                        }
                    }

                    items.itemCount == 0 && !items.loadState.isLoading -> {
                        item {
                            TextInfoView(
                                modifier = Modifier.fillParentMaxSize(),
                                info = stringResource(R.string.error_empty_list)
                            )
                        }
                    }
                }
            }
        }
    }
}



@Preview
@Composable
fun ApiPlaylistScreenPreview() {
    AvitoPlayerTheme {
        //ApiPlaylistScreen(items = listOf())
    }
}
