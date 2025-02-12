package com.manyacov.feature_api_tracks.presentation.mapper

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.manyacov.domain.avito_player.utils.UiIssues
import com.manyacov.resources.R

@Composable
fun UiIssues.toStringDescription(): String {
    return when(this) {
        UiIssues.EMPTY_RESULT -> stringResource(R.string.error_empty_list)
        UiIssues.NO_INTERNET_ERROR -> stringResource(R.string.error_internet)
        else -> stringResource(R.string.unknown_error)
    }
}