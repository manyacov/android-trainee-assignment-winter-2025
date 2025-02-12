package com.manyacov.data.avito_player.utils

import com.manyacov.domain.avito_player.utils.CustomResult
import com.manyacov.domain.avito_player.utils.DataIssues

internal fun <T : Any, R> Result<T>.toRequestResult(dataMapper: (T) -> R): CustomResult<R> {
    return when {
        isSuccess -> {
            val data = this.getOrNull()
            data?.let {
                CustomResult.Success(dataMapper(it))
            } ?: CustomResult.Error("No data", DataIssues.UNKNOWN_ERROR)
        }
        else -> {
            val exception = this.exceptionOrNull()
            val message = exception?.message ?: "Unknown error"
            val issueType = mapExceptionToIssueType(exception)
            CustomResult.Error(message, issueType)
        }
    }
}

private fun mapExceptionToIssueType(exception: Throwable?): DataIssues {
    return when (exception) {
        is java.net.SocketTimeoutException -> DataIssues.REQUEST_TIMEOUT_ERROR
        is java.net.UnknownHostException -> DataIssues.NO_INTERNET_ERROR
        is retrofit2.HttpException -> { DataIssues.SERVER_ERROR }
        is kotlinx.serialization.SerializationException -> DataIssues.SERIALIZATION_ERROR
        else -> DataIssues.UNKNOWN_ERROR
    }
}