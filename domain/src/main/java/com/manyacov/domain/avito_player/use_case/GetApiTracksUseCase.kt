package com.manyacov.domain.avito_player.use_case

import com.manyacov.common.domain.BaseUseCase
import com.manyacov.domain.avito_player.model.PlaylistTrack
import com.manyacov.domain.avito_player.repository.PlaylistRepository

class GetApiTracksUseCase(
    private val playlistRepository: PlaylistRepository
) : BaseUseCase<List<PlaylistTrack>, GetApiTracksUseCase.Params>() {

    override suspend fun execute(params: Params): List<PlaylistTrack> {
        var list: List<PlaylistTrack> = listOf()
        playlistRepository.getChartTracks().collect { list = it }
        return list
    }

    data object Params
}