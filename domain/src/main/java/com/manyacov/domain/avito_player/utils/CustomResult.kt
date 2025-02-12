package com.manyacov.domain.avito_player.utils

sealed class CustomResult<T>(
    val data: T? = null,
    val message: String? = null,
    val issueType: DataIssues? = null
) {
    class Success<T>(data: T?) : CustomResult<T>(data)
    class Error<T>(message: String? = null, issueType: DataIssues?) : CustomResult<T>(null, message, issueType)
}