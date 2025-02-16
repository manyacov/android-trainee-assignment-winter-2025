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
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.manyacov.common.NavPath
import com.manyacov.domain.avito_player.utils.UiIssues
import com.manyacov.feature_downloaded_tracks.presentation.mapper.toStringDescription

@SuppressLint("InlinedApi")
@Composable
fun DownloadedScreen(
    modifier: Modifier = Modifier,
    viewModel: DownloadedViewModel,
    navController: NavController
) {
    val state by viewModel.uiState.collectAsState()

    val permissionsToCheck = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.READ_MEDIA_AUDIO
    )

    var hasPermission by remember { mutableStateOf(false) }
    var requestPermissions by remember { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        hasPermission = permissions.entries.any { entry -> entry.value }
        if (hasPermission) {
            Log.println(Log.ERROR, "TTTTTT", "")
            viewModel.setEvent(DownloadedPlaylistContract.Event.OnReloadClicked)
        }
    }

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        hasPermission = permissionsToCheck.any {
            ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }

        if (!hasPermission) {
            requestPermissions = true
            permissionLauncher.launch(permissionsToCheck)
        } else {
            viewModel.setEvent(DownloadedPlaylistContract.Event.OnReloadClicked)
        }
    }

    DownloadedScreen(
        modifier = modifier,
        isLoading = state.isLoading,
        playlist = state.playlist,
        searchString = state.searchString,
        isPermissionRejected = state.isPermissionsRejected,
        onReloadClicked = {
            permissionLauncher.launch(permissionsToCheck)
        },
        onSearchClicked = { viewModel.setEvent(DownloadedPlaylistContract.Event.OnSearchClicked) },
        onTrackClicked = { path ->
            viewModel.setEvent(DownloadedPlaylistContract.Event.OnTrackClicked(path))
            navController.navigate(NavPath.LOCAL_PLAYER)
        },
        onSearchValueChange = {
            viewModel.setEvent(DownloadedPlaylistContract.Event.UpdateSearchText(it))
        }
    )
}

@Composable
internal fun DownloadedScreen(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    playlist: List<TrackItem>,
    searchString: String = "",
    isPermissionRejected: Boolean,
    onReloadClicked: () -> Unit = {},
    onSearchClicked: () -> Unit = {},
    onTrackClicked: (String) -> Unit = {},
    onSearchValueChange: (String) -> Unit = {}
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        }

        SearchPlaylist(
            modifier = Modifier.padding(LocalDim.current.spaceSize16),
            trackList = playlist,
            onReloadClicked = onReloadClicked,
            onSearchClicked = onSearchClicked,
            onTrackClicked = onTrackClicked,
            searchString = searchString,
            onSearchValueChange = onSearchValueChange,
            isError = isPermissionRejected,
            errorDescription = UiIssues.EMPTY_RESULT.toStringDescription()
        )
    }
}

@Preview
@Composable
fun DownloadedScreenPreview() {
    AvitoPlayerTheme {
        DownloadedScreen(
            isLoading = true,
            isPermissionRejected = true,
            playlist = listOf()
        )
    }
}
