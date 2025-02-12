package com.manyacov.domain.avito_player.repository

import com.manyacov.domain.avito_player.model.PlaylistTrack
import com.manyacov.domain.avito_player.utils.CustomResult
import kotlinx.coroutines.flow.Flow

interface PlaylistRepository {

    suspend fun getChartTracks(): Flow<CustomResult<List<PlaylistTrack>>>
}