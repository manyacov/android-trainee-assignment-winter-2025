package com.manyacov.domain.avito_player.use_case

import com.manyacov.common.domain.BaseFlowUseCase
import com.manyacov.domain.avito_player.repository.LocalPlaylistRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrentTrackListUseCase @Inject constructor(
    private val localRepository: LocalPlaylistRepository
) : BaseFlowUseCase<List<String>, GetCurrentTrackListUseCase.Params>() {

    override fun execute(params: Params): Flow<List<String>> {
        return localRepository.getCurrentPlaylist()
    }

    data object Params
}