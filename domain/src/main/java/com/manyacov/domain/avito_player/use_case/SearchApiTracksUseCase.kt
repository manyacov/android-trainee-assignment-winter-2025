package com.manyacov.domain.avito_player.use_case

import com.manyacov.common.domain.BaseUseCase
import com.manyacov.domain.avito_player.model.PlaylistTrack
import com.manyacov.domain.avito_player.repository.PlaylistRepository
import javax.inject.Inject

class SearchApiTracksUseCase @Inject constructor(
    private val playlistRepository: PlaylistRepository
) : BaseUseCase<List<PlaylistTrack>, SearchApiTracksUseCase.Params>() {

    override suspend fun execute(params: Params): List<PlaylistTrack> {
        var list: List<PlaylistTrack> = listOf()
        playlistRepository.searchTracks(params.searchString).collect { list = it.data ?: listOf() }
        return list
    }

    data class Params(val searchString: String)
}