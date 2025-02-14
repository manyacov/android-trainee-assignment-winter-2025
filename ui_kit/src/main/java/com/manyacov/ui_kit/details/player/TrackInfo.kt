package com.manyacov.ui_kit.details.player

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.manyacov.resources.theme.AvitoPlayerTheme
import com.manyacov.resources.theme.LocalDim
import com.manyacov.resources.theme.LocalTextDim

@Composable
fun TrackInfo(
    modifier: Modifier = Modifier,
    title: String,
    artistName: String
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(LocalDim.current.spaceSize4)
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = LocalTextDim.current.textSize18
        )
        Text(
            text = artistName,
            overflow = TextOverflow.Ellipsis,
            fontSize = LocalTextDim.current.textSize16,
            maxLines = 1
        )
    }
}

@Composable
@Preview
fun TrackInfoPreview() {
    AvitoPlayerTheme {
        TrackInfo(
            title = "Monica (Demo)",
            artistName = "Imagine Dragons"
        )
    }
}