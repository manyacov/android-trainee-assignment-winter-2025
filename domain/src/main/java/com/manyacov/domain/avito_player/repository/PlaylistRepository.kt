package com.manyacov.domain.avito_player.repository

import com.manyacov.domain.avito_player.model.PlaylistTrack
import kotlinx.coroutines.flow.Flow

interface PlaylistRepository {

    suspend fun getChartTracks(): Flow<List<PlaylistTrack>>
}