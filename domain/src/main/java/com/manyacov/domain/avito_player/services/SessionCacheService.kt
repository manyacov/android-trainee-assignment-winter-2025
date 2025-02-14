package com.manyacov.domain.avito_player.services

import kotlinx.coroutines.flow.Flow

interface SessionCacheService {

    suspend fun saveTrackPath(path: String)
    fun getTrackPath(): Flow<String?>

    suspend fun clearSession()
}