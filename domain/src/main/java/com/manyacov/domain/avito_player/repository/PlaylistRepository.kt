package com.manyacov.domain.avito_player.repository

import androidx.paging.PagingData
import com.manyacov.domain.avito_player.model.PlaylistTrack
import com.manyacov.domain.avito_player.utils.CustomResult
import kotlinx.coroutines.flow.Flow

interface PlaylistRepository {

    suspend fun getChartTracks(): Flow<CustomResult<List<PlaylistTrack>>>

    suspend fun searchTracks(search: String): Flow<CustomResult<List<PlaylistTrack>>>

    fun searchSongs(searchText: String): Flow<PagingData<PlaylistTrack>>

}