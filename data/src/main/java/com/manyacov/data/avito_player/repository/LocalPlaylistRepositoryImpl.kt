package com.manyacov.data.avito_player.repository

import com.manyacov.data.avito_player.datasource.local.dao.AvitoPlayerDao
import com.manyacov.data.avito_player.mapper.toPlaylistInfoEntity
import com.manyacov.domain.avito_player.repository.LocalPlaylistRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalPlaylistRepositoryImpl @Inject constructor(
    private val localSource: AvitoPlayerDao
): LocalPlaylistRepository {

    override suspend fun saveCurrentPlaylist(paths: List<String>) {
        localSource.clearPlaylistTable()
        localSource.saveCurrentPlaylist(paths.map { it.toPlaylistInfoEntity() })
    }

    override fun getCurrentPlaylist(): Flow<List<String>> {
        return localSource.getCurrentPlaylistInfo().map { list -> list.map { it.path } }
    }
}