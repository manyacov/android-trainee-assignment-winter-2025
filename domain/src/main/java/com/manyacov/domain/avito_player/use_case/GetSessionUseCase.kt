package com.manyacov.domain.avito_player.use_case

import com.manyacov.common.domain.BaseUseCase
import com.manyacov.domain.avito_player.services.SessionCacheService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSessionUseCase @Inject constructor(
    private val sessionCacheService: SessionCacheService
) : BaseUseCase<Flow<String?>, GetSessionUseCase.Params>() {

    override suspend fun execute(params: Params): Flow<String?> {
        return sessionCacheService.getTrackPath()
    }
    data object Params
}