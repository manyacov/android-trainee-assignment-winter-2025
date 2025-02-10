package com.manyacov.ui_kit.list_items

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.manyacov.resources.theme.AvitoPlayerTheme
import com.manyacov.resources.theme.LocalTextDim

@Composable
fun PlaylistItem(
    modifier: Modifier = Modifier,
    //audioItem: AudioItem
) {
    Row(modifier = modifier.fillMaxWidth()) {

        Column {
            Text(text = "audioItem.name", fontWeight = FontWeight.Bold, fontSize = LocalTextDim.current.textSize16)
            Text(text = "audioItem.authorName", fontSize = LocalTextDim.current.textSize12)
        }
    }
}

@Preview
@Composable
fun PlayListItemPreview() {
    AvitoPlayerTheme {
        PlaylistItem()
    }
}