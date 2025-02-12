package com.manyacov.data.avito_player.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.manyacov.common.Constants
import com.manyacov.data.avito_player.datasource.local.model.TrackEntity
import com.manyacov.data.avito_player.datasource.remote.api.PlaylistApi
import com.manyacov.data.avito_player.datasource.remote.model.TrackDto
import com.manyacov.data.avito_player.mapper.toPlaylistTrack
import com.manyacov.data.avito_player.paging.SearchTracksSource
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
        val apiResult = playlistApi.getSearchedList(search, 25)

        val result = apiResult.toRequestResult { data ->
            data.data.map { it.toPlaylistTrack() }
        }

        return flow { emit(result) }
    }

    override fun searchSongs(
        searchText: String,
    ): Flow<PagingData<PlaylistTrack>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.PAGE_SIZE,
                prefetchDistance = Constants.PREFETCH_DISTANCE,
                initialLoadSize = Constants.INITIAL_PAGE_SIZE
            ),
            pagingSourceFactory = { SearchTracksSource(searchText, playlistApi) }
        ).flow
    }
}
