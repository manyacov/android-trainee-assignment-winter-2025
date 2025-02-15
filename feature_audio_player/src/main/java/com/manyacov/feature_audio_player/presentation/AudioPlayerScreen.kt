package com.manyacov.feature_audio_player.presentation

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.net.toUri
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.manyacov.resources.R
import com.manyacov.feature_audio_player.presentation.model.Audio
import com.manyacov.resources.theme.AvitoPlayerTheme
import com.manyacov.resources.theme.LocalDim
import com.manyacov.resources.theme.color.setThemeSliderColors
import com.manyacov.ui_kit.details.player.TrackInfo
import com.manyacov.ui_kit.details.player.MediaPlayerController

private val audioDummy = Audio(
    "".toUri(), "", 0L, "", "", ""
)

@Composable
fun AudioPlayerScreen(
    modifier: Modifier,
    viewModel: AudioPlayerViewModel
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.setEvent(AudioPlayerContract.Event.OnScreenOpened)
    }

    AudioPlayerScreen(
        modifier = modifier,
        audioList = viewModel.audioList,
        duration = viewModel.duration,
        progress = viewModel.progress,
        progressMils = viewModel.progressMils,
        onProgress = { viewModel.setEvent(AudioPlayerContract.Event.OnChangeProgress(it) )},
        isAudioPlaying = viewModel.isPlaying,
        onStart = { viewModel.setEvent(AudioPlayerContract.Event.OnPlayPauseClicked) },
        onNext = {}
    )
}

@Composable
internal fun AudioPlayerScreen(
    modifier: Modifier = Modifier,
    audioList: List<Audio>,
    duration: Long,
    progress: Float,
    progressMils: Long,
    onProgress: (Float) -> Unit = {},
    isAudioPlaying: Boolean,
    onStart: () -> Unit = {},
    onPrevious: () -> Unit = {},
    onNext: () -> Unit = {},
) {
    val audio = if (audioList.isNotEmpty()) audioList.first() else audioDummy

    Column(
        modifier = modifier.fillMaxSize().padding(horizontal = LocalDim.current.spaceSize16),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AsyncImage(
            modifier = Modifier
                .padding(LocalDim.current.spaceSize36)
                .aspectRatio(1f)
                .clip(RoundedCornerShape(LocalDim.current.spaceSize14))
                .background(MaterialTheme.colorScheme.tertiary),
            model = ImageRequest.Builder(LocalContext.current)
                .data(audio.imageUrl)
                .crossfade(true)
                .build(),
            placeholder = painterResource(id = R.drawable.ic_placeholer),
            contentDescription = null,
            error = painterResource(id = R.drawable.ic_placeholer)
        )

        TrackInfo(
            title = audio.title,
            artistName = audio.artist
        )

        Slider(
            //modifier = Modifier.padding(vertical = LocalDim.current.spaceSize24),
            value = progress,
            onValueChange = { onProgress(it) },
            valueRange = 0f..100f,
            colors = setThemeSliderColors()
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = formatTime(progressMils.toInt() / 1000))
            Text(text = formatTime((duration.toInt() - progressMils.toInt()) / 1000))
        }

        MediaPlayerController(
            modifier = Modifier.padding(vertical = LocalDim.current.spaceSize24),
            isAudioPlaying = isAudioPlaying,
            onStart = onStart,
            onPrevious = onPrevious,
            onNext = onNext
        )
    }
}

fun formatTime(seconds: Int): String {
    val minutes = seconds / 60
    val secs = seconds % 60
    return String.format("%02d:%02d", minutes, secs)
}

@Composable
@Preview
fun AudioPlayerScreenPreview() {
    val audio = Audio(
        uri = "".toUri(),
        displayName = "Monica (Demo)",
        id = 1L,
        artist = "Imagine Dragons",
        //duration = 100,
        title = "Monica (Demo)",
        imageUrl = ""
    )

    AvitoPlayerTheme {
        AudioPlayerScreen(
            duration = 3L,
            audioList = listOf(audio),
            progress = 50f,
            progressMils = 0L,
            isAudioPlaying = false,
        )
    }
}