package com.manyacov.domain.avito_player.use_case

import com.manyacov.common.domain.BaseUseCase
import com.manyacov.domain.avito_player.services.SessionCacheService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetSessionUseCase @Inject constructor(
    private val sessionCacheService: SessionCacheService
) : BaseUseCase<Flow<Pair<String?, Boolean?>>, GetSessionUseCase.Params>() {

    override suspend fun execute(params: Params): Flow<Pair<String?, Boolean?>> {
        return sessionCacheService.getTrackPath()
            .combine(sessionCacheService.getIsLocal()) { path, isLocal ->
                path to isLocal
            }
    }

    data object Params
}