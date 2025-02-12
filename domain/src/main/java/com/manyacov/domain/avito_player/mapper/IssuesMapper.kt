package com.manyacov.domain.avito_player.mapper

import com.manyacov.domain.avito_player.utils.DataIssues
import com.manyacov.domain.avito_player.utils.UiIssues

fun DataIssues?.toUiIssuesType(): UiIssues {
    return when (this) {
        DataIssues.REQUEST_TIMEOUT_ERROR -> UiIssues.REQUEST_TIMEOUT_ERROR
        DataIssues.NO_INTERNET_ERROR -> UiIssues.NO_INTERNET_ERROR
        DataIssues.SERVER_ERROR -> UiIssues.SERVER_ERROR
        DataIssues.SERIALIZATION_ERROR -> UiIssues.SERIALIZATION_ERROR
        else -> UiIssues.UNKNOWN_ERROR
    }
}