package com.manyacov.ui_kit.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.manyacov.resources.R
import com.manyacov.resources.theme.LocalDim

@Composable
fun TextInfoView(modifier: Modifier) {
    Box(modifier = modifier) {
        Text(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = LocalDim.current.spaceSize36),
            text = stringResource(R.string.error_rejected_permissions),
            textAlign = TextAlign.Center
        )
    }
}