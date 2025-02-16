package com.manyacov.ui_kit.details.player

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.manyacov.resources.R
import com.manyacov.resources.theme.AvitoPlayerTheme
import com.manyacov.resources.theme.LocalDim

@Composable
fun TrackCover(
    modifier: Modifier = Modifier,
    imageUrl: String = ""
) {
    Box(
        modifier = modifier
            .background(MaterialTheme.colorScheme.tertiary)
            .clip(RoundedCornerShape(LocalDim.current.spaceSize14)),
    ) {
        AsyncImage(
            modifier = Modifier
                .padding(LocalDim.current.spaceSize36)
                .aspectRatio(1f)
                .clip(RoundedCornerShape(LocalDim.current.spaceSize14)),
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            placeholder = painterResource(id = R.drawable.ic_placeholer),
            contentDescription = null,
            error = painterResource(id = R.drawable.ic_placeholer)
        )
    }
}

@Preview
@Composable
fun TrackCoverPreview() {
    AvitoPlayerTheme {
        TrackCover()
    }
}