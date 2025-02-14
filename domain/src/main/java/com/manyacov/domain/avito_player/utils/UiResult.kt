package com.manyacov.domain.avito_player.utils

sealed class UiResult<T>(
    val data: T? = null,
    val issueType: UiIssues? = null
) {
    class Success<T>(data: T?) : UiResult<T>(data)
    class Error<T>(issueType: UiIssues?) : UiResult<T>(null, issueType)
    class Empty<T> : UiResult<T>()
}