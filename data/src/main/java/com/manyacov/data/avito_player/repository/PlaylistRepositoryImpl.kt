package com.manyacov.data.avito_player.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.manyacov.common.Constants
import com.manyacov.data.avito_player.datasource.remote.api.PlaylistApi
import com.manyacov.data.avito_player.paging.SearchTracksSource
import com.manyacov.domain.avito_player.model.PlaylistTrack
import com.manyacov.domain.avito_player.repository.PlaylistRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PlaylistRepositoryImpl @Inject constructor(
    private val playlistApi: PlaylistApi
): PlaylistRepository {

    override fun searchTracks(searchText: String): Flow<PagingData<PlaylistTrack>> {
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
