package com.manyacov.domain.avito_player.use_case

import com.manyacov.common.domain.BaseUseCase
import com.manyacov.domain.avito_player.repository.LocalPlaylistRepository
import javax.inject.Inject

class SaveCurrentTrackList @Inject constructor(
    private val localRepository: LocalPlaylistRepository
) : BaseUseCase<Unit, SaveCurrentTrackList.Params>() {

    override suspend fun execute(params: Params) {
        localRepository.saveCurrentPlaylist(params.paths)
    }

    data class Params(val paths: List<String>)
}