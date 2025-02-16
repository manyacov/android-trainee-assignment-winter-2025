package com.manyacov.domain.avito_player.use_case

import com.manyacov.common.domain.BaseFlowUseCase
import com.manyacov.domain.avito_player.repository.LocalPlaylistRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCurrentTrackListPartUseCase @Inject constructor(
    private val localRepository: LocalPlaylistRepository
) : BaseFlowUseCase<List<String>, GetCurrentTrackListPartUseCase.Params>() {

    override fun execute(params: Params): Flow<List<String>> {
        return flow { emit(localRepository.getCurrentPartPlaylist(params.id)) }
    }

    data class Params(val id: String)
}