package com.manyacov.data.avito_player.repository

import com.manyacov.data.avito_player.datasource.remote.api.PlaylistApi
import com.manyacov.data.avito_player.mapper.toPlaylistTrack
import com.manyacov.data.avito_player.utils.toRequestResult
import com.manyacov.domain.avito_player.model.PlaylistTrack
import com.manyacov.domain.avito_player.repository.PlaylistRepository
import com.manyacov.domain.avito_player.utils.CustomResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PlaylistRepositoryImpl @Inject constructor(
    private val playlistApi: PlaylistApi
): PlaylistRepository {

    override suspend fun getChartTracks(): Flow<CustomResult<List<PlaylistTrack>>> {
        val apiResult = playlistApi.getChartList()

        val result = apiResult.toRequestResult { data ->
            data.tracks.data.map { it.toPlaylistTrack() }
        }

        return flow { emit(result) }
    }

    override suspend fun searchTracks(search: String): Flow<CustomResult<List<PlaylistTrack>>> {
        val apiResult = playlistApi.getSearchedList(search)

        val result = apiResult.toRequestResult { data ->
            data.tracks.data.map { it.toPlaylistTrack() }
        }

        return flow { emit(result) }
    }
}
