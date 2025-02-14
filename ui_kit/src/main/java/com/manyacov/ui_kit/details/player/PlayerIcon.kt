package com.manyacov.ui_kit.details.player

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.manyacov.resources.theme.AvitoPlayerTheme
import com.manyacov.resources.R
import com.manyacov.resources.theme.LocalDim

@Composable
fun PlayerIcon(
    modifier: Modifier = Modifier,
    iconRes: Int,
    tint: Color = MaterialTheme.colorScheme.secondary,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .padding(LocalDim.current.spaceSize4)
            .clickable { onClick() },
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            tint = tint,
            contentDescription = null
        )
    }
}

@Composable
@Preview
fun PlayerIconPreview() {
    AvitoPlayerTheme {
        PlayerIcon(iconRes = R.drawable.ic_pause) { }
    }
}