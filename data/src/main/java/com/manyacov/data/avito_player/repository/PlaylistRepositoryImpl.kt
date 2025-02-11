package com.manyacov.data.avito_player.repository

import com.manyacov.data.avito_player.datasource.remote.api.PlaylistApi
import com.manyacov.data.avito_player.mapper.toPlaylistTrack
import com.manyacov.domain.avito_player.model.PlaylistTrack
import com.manyacov.domain.avito_player.repository.PlaylistRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PlaylistRepositoryImpl @Inject constructor(
    private val api: PlaylistApi
): PlaylistRepository {

    override suspend fun getChartTracks(): Flow<List<PlaylistTrack>> = withContext(Dispatchers.IO) {
        val result = api.getChartList()

        val list = if (result.isSuccessful) {
            result.body()?.map { it.toPlaylistTrack() } ?: listOf()
        } else {
            listOf()
        }
        flow { emit(list) }
    }

}
