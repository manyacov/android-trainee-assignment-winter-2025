package com.manyacov.data.avito_player.repository

import com.manyacov.data.avito_player.datasource.local.dao.AvitoPlayerDao
import com.manyacov.data.avito_player.mapper.toPlaylistInfoEntity
import com.manyacov.domain.avito_player.repository.LocalPlaylistRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalPlaylistRepositoryImpl @Inject constructor(
    private val localSource: AvitoPlayerDao
) : LocalPlaylistRepository {

    override suspend fun saveCurrentPlaylist(paths: List<String>) {
        localSource.clearPlaylistTable()
        localSource.saveCurrentPlaylist(paths.map { it.toPlaylistInfoEntity() })
    }

    override fun getCurrentPlaylist(): Flow<List<String>> {
        return localSource.getCurrentPlaylistInfo().map { list -> list.map { it.path } }
    }

    override suspend fun getCurrentPartPlaylist(trackId: String): List<String> {
        val selectedTrack = localSource.getTrackById(trackId) ?: return emptyList()
        val selectedTrackId = selectedTrack.id

        val previousTracks = localSource.getPreviousTwoTracks(currentId = selectedTrackId, limit = 2)
        val nextTracks = localSource.getNextTwoTracks(currentId = selectedTrackId, limit = 2)

        return (previousTracks.map { it.path }).reversed() + selectedTrack.path + nextTracks.map { it.path }
    }

    override suspend fun getPreviousTrackId(trackId: String): String? {
        val selectedTrack = localSource.getTrackById(trackId)
        val previous = localSource.getPreviousOne(currentId = selectedTrack?.id, limit = 1)?.path

        return previous
    }

    override suspend fun getNextTrackId(trackId: String): String? {
        val selectedTrack = localSource.getTrackById(trackId)
        val next = localSource.getNextOne(currentId = selectedTrack?.id, limit = 1)?.path

        return next
    }
}