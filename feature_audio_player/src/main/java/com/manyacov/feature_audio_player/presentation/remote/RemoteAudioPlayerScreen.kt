package com.manyacov.feature_audio_player.presentation.remote

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.manyacov.feature_audio_player.presentation.AudioPlayerContract
import com.manyacov.feature_audio_player.presentation.AudioPlayerScreen
import com.manyacov.feature_audio_player.presentation.AudioPlayerViewModel
import com.manyacov.feature_audio_player.presentation.model.Audio

private val audioDummy = Audio(
    "".toUri(), "", 0L, "", "", ""
)

@SuppressLint("InlinedApi")
@Composable
fun RemoteAudioPlayerScreen(
    modifier: Modifier,
    viewModel: RemoteAudioPlayerViewModel
) {
    val state by viewModel.uiState.collectAsState()

    val notificationPermission = Manifest.permission.POST_NOTIFICATIONS

    var hasPermission by remember { mutableStateOf(false) }
    var requestPermissions by remember { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasPermission = isGranted
        viewModel.setEvent(AudioPlayerContract.Event.OnScreenOpened)
    }

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        hasPermission = ContextCompat.checkSelfPermission(
            context,
            notificationPermission
        ) == PackageManager.PERMISSION_GRANTED

        if (!hasPermission) {
            requestPermissions = true
            permissionLauncher.launch(notificationPermission)
        } else {
            viewModel.setEvent(AudioPlayerContract.Event.OnScreenOpened)
        }
    }

    AudioPlayerScreen(
        modifier = modifier,
        isLoading = state.isLoading,
        currentTrack = viewModel.currentSelectedAudio,
        playerTime = state.playerTime,
        progress = viewModel.progress,
        onProgress = { viewModel.setEvent(AudioPlayerContract.Event.OnChangeProgress(it)) },
        isAudioPlaying = viewModel.isPlaying,
        onStart = { viewModel.setEvent(AudioPlayerContract.Event.OnPlayPauseClicked) },
        onPrevious = { currentTrackId ->
            viewModel.setEvent(
                AudioPlayerContract.Event.OnPreviousClicked(
                    currentTrackId
                )
            )
        },
        onNext = { currentTrackId ->
            viewModel.setEvent(
                AudioPlayerContract.Event.OnNextClicked(
                    currentTrackId
                )
            )
        }
    )
}