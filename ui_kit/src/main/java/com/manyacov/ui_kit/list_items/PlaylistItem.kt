package com.manyacov.ui_kit.list_items

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.manyacov.resources.theme.AvitoPlayerTheme
import com.manyacov.resources.theme.LocalDim
import com.manyacov.resources.theme.LocalTextDim
import androidx.compose.foundation.layout.size
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import com.manyacov.resources.R

@Composable
fun PlaylistItem(
    modifier: Modifier = Modifier,
    trackItem: TrackItem,
    onClick: (String) -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick(trackItem.id) }
            .padding(vertical = LocalDim.current.spaceSize8),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier
                .padding(horizontal = LocalDim.current.spaceSize4)
                .size(LocalDim.current.spaceSize58)
                .clip(RoundedCornerShape(LocalDim.current.spaceSize14))
                .background(MaterialTheme.colorScheme.tertiary)
                .padding(horizontal = LocalDim.current.spaceSize12),
            model = ImageRequest.Builder(LocalContext.current)
                .data(trackItem.imageUrl)
                .crossfade(true)
                .build(),
            placeholder = painterResource(id = R.drawable.ic_placeholer),
            contentDescription = null,
            error = painterResource(id = R.drawable.ic_placeholer),
        )

        Column(modifier = Modifier.padding(horizontal = LocalDim.current.spaceSize8)) {
            Text(
                text = trackItem.title,
                fontWeight = FontWeight.Bold,
                fontSize = LocalTextDim.current.textSize16,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            
            Text(
                text = trackItem.artistName,
                fontSize = LocalTextDim.current.textSize12,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview
@Composable
fun PlayListItemPreview() {
    AvitoPlayerTheme {
        PlaylistItem(
            trackItem = TrackItem(
                id = "",
                title = "Monica (Demo)",
                artistName = "Imagine Dragons",
                imageUrl = "https://api.deezer.com/album/1347315/image"
            )
        )
    }
}