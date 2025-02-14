package com.manyacov.ui_kit.details.player

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.manyacov.resources.R
import com.manyacov.resources.theme.AvitoPlayerTheme
import com.manyacov.resources.theme.LocalDim

@Composable
fun MediaPlayerController(
    modifier: Modifier = Modifier,
    isAudioPlaying: Boolean,
    onPrevious: () -> Unit = {},
    onStart: () -> Unit = {},
    onNext: () -> Unit = {},
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(LocalDim.current.spaceSize24)
    ) {
        Icon(
            modifier = Modifier
                .size(LocalDim.current.spaceSize48)
                .clickable { onPrevious() },
            painter = painterResource(R.drawable.ic_previous),
            tint = MaterialTheme.colorScheme.secondary,
            contentDescription = null
        )

        PlayerIcon(
            modifier = Modifier.size(LocalDim.current.spaceSize58),
            iconRes = if (isAudioPlaying) R.drawable.ic_pause
            else R.drawable.ic_play
        ) {
            onStart()
        }

        Icon(
            modifier = Modifier
                .size(LocalDim.current.spaceSize48)
                .clickable { onNext() },
            painter = painterResource(R.drawable.ic_next),
            tint = MaterialTheme.colorScheme.secondary,
            contentDescription = null
        )
    }
}

@Composable
@Preview
fun MediaPlayerControllerPreview() {
    AvitoPlayerTheme {
        MediaPlayerController(isAudioPlaying = true) { }
    }
}
