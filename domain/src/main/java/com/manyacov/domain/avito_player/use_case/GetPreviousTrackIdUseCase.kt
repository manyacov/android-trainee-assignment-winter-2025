package com.manyacov.domain.avito_player.use_case

import com.manyacov.common.domain.BaseFlowUseCase
import com.manyacov.domain.avito_player.repository.LocalPlaylistRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPreviousTrackIdUseCase @Inject constructor(
    private val localRepository: LocalPlaylistRepository
) : BaseFlowUseCase<String?, GetPreviousTrackIdUseCase.Params>() {

    override fun execute(params: Params): Flow<String?> {
        return flow { emit(localRepository.getPreviousTrackId(params.id)) }
    }

    data class Params(val id: String)
}