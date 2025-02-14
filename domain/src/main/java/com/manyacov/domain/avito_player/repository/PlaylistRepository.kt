package com.manyacov.domain.avito_player.repository

import androidx.paging.PagingData
import com.manyacov.domain.avito_player.model.PlaylistTrack
import com.manyacov.domain.avito_player.utils.CustomResult
import kotlinx.coroutines.flow.Flow

interface PlaylistRepository {
    fun searchTracks(searchText: String): Flow<PagingData<PlaylistTrack>>
}