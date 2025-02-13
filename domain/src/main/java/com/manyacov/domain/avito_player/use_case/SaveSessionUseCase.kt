package com.manyacov.domain.avito_player.use_case

import com.manyacov.common.domain.BaseUseCase
import com.manyacov.domain.avito_player.services.SessionCacheService
import javax.inject.Inject

class SaveSessionUseCase @Inject constructor(
    private val sessionCacheService: SessionCacheService
) : BaseUseCase<Unit, SaveSessionUseCase.Params>() {

    override suspend fun execute(params: Params) {
        sessionCacheService.saveTrackPath(params.path)
    }
    data class Params(val path: String)
}