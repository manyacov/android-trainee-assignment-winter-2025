package com.manyacov.domain.avito_player.repository

import kotlinx.coroutines.flow.Flow

interface LocalPlaylistRepository {

    suspend fun saveCurrentPlaylist(paths: List<String>)
    fun getCurrentPlaylist(): Flow<List<String>>

    suspend fun getCurrentPartPlaylist(trackId: String): List<String>

    suspend fun getPreviousTrackId(trackId: String): String?
    suspend fun getNextTrackId(trackId: String): String?
}