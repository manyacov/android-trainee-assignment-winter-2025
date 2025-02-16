package com.manyacov.feature_audio_player.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.manyacov.feature_audio_player.presentation.model.PlayerTime
import com.manyacov.feature_audio_player.presentation.model.audioDummy
import com.manyacov.resources.theme.AvitoPlayerTheme
import com.manyacov.resources.theme.LocalDim
import com.manyacov.resources.theme.color.setThemeSliderColors
import com.manyacov.ui_kit.details.player.TrackInfo
import com.manyacov.ui_kit.details.player.MediaPlayerController

@Composable
internal fun AudioPlayerScreen(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    currentTrack: Audio = audioDummy,
    playerTime: PlayerTime,
    progress: Float,
    onProgress: (Float) -> Unit = {},
    isAudioPlaying: Boolean,
    onStart: () -> Unit = {},
    onPrevious: (String) -> Unit = {},
    onNext: (String) -> Unit = {},
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center)
    {
        if (isLoading) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = LocalDim.current.spaceSize16),
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
                    .data(currentTrack.imageUrl)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(id = R.drawable.ic_placeholer),
                contentDescription = null,
                error = painterResource(id = R.drawable.ic_placeholer)
            )

            TrackInfo(
                title = currentTrack.title,
                artistName = currentTrack.artist
            )

            Slider(
                value = progress,
                onValueChange = { onProgress(it) },
                valueRange = 0f..100f,
                colors = setThemeSliderColors()
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = playerTime.playingTime)
                Text(text = playerTime.restTime)
            }

            MediaPlayerController(
                modifier = Modifier.padding(vertical = LocalDim.current.spaceSize24),
                isAudioPlaying = isAudioPlaying,
                onStart = onStart,
                onPrevious = { onPrevious(currentTrack.id.toString()) },
                onNext = { onNext(currentTrack.id.toString()) }
            )
        }
    }
}

@Composable
@Preview
fun AudioPlayerScreenPreview() {
    val audio = Audio(
        uri = "".toUri(),
        displayName = "Monica (Demo)",
        id = 1L,
        artist = "Imagine Dragons",
        title = "Monica (Demo)",
        imageUrl = ""
    )

    AvitoPlayerTheme {
        AudioPlayerScreen(
            isLoading = true,
            playerTime = PlayerTime("00:00", "- 00:00"),
            currentTrack = audio,
            progress = 50f,
            isAudioPlaying = false,
        )
    }
}