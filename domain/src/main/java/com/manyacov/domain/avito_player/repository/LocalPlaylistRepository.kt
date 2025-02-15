package com.manyacov.domain.avito_player.repository

import kotlinx.coroutines.flow.Flow

interface LocalPlaylistRepository {

    suspend fun saveCurrentPlaylist(paths: List<String>)
    fun getCurrentPlaylist(): Flow<List<String>>
}