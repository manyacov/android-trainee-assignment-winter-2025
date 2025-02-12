package com.manyacov.feature_downloaded_tracks.presentation

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.manyacov.resources.theme.AvitoPlayerTheme
import com.manyacov.resources.theme.LocalDim
import com.manyacov.ui_kit.components.SearchPlaylist
import com.manyacov.ui_kit.list_items.TrackItem
import android.Manifest
import android.os.Build
import com.manyacov.domain.avito_player.utils.UiIssues
import com.manyacov.feature_downloaded_tracks.presentation.mapper.toStringDescription

@Composable
fun DownloadedScreen(
    modifier: Modifier = Modifier,
    viewModel: DownloadedViewModel
) {
    val state by viewModel.uiState.collectAsState()

    var permissionGranted by remember { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        permissionGranted = isGranted

        if (permissionGranted) {
            viewModel.setEvent(DownloadedPlaylistContract.Event.OnReloadClicked)
        } else {
            viewModel.setEvent(DownloadedPlaylistContract.Event.OnRejectedPermissions)
        }
    }

    LaunchedEffect(Unit) {
        if (!permissionGranted) {
            permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

    DownloadedScreen(
        modifier = modifier,
        playlist = state.playlist,
        searchString = state.searchString,
        isPermissionRejected = state.isPermissionsRejected,
        onReloadClicked = {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            } else {
                permissionLauncher.launch(Manifest.permission.READ_MEDIA_AUDIO)
            }
        },
        onSearchClicked = { viewModel.setEvent(DownloadedPlaylistContract.Event.OnSearchClicked) },
        onSearchValueChange = {
            viewModel.setEvent(DownloadedPlaylistContract.Event.UpdateSearchText(it))
        }
    )
}

@Composable
internal fun DownloadedScreen(
    modifier: Modifier = Modifier,
    playlist: List<TrackItem>,
    searchString: String = "",
    isPermissionRejected: Boolean,
    onReloadClicked: () -> Unit = {},
    onSearchClicked: () -> Unit = {},
    onSearchValueChange: (String) -> Unit = {}
) {
    SearchPlaylist(
        modifier = modifier.padding(LocalDim.current.spaceSize16),
        trackList = playlist,
        onReloadClicked = onReloadClicked,
        onSearchClicked = onSearchClicked,
        searchString = searchString,
        onSearchValueChange = onSearchValueChange,
        isError = isPermissionRejected,
        errorDescription = UiIssues.PERMISSION_REJECTED_ERROR.toStringDescription()
    )
}

@Preview
@Composable
fun DownloadedScreenPreview() {
    AvitoPlayerTheme {
        DownloadedScreen(
            isPermissionRejected = true,
            playlist = listOf()
        )
    }
}
