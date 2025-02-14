package com.manyacov.feature_downloaded_tracks.presentation.mapper

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.manyacov.domain.avito_player.utils.UiIssues
import com.manyacov.resources.R

@Composable
fun UiIssues.toStringDescription(): String {
    return when(this) {
        UiIssues.PERMISSION_REJECTED_ERROR -> stringResource(R.string.error_rejected_permissions)
        else -> stringResource(R.string.unknown_error)
    }
}