package com.manyacov.common.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch

abstract class BaseFlowUseCase<out Type, in Params> {
    operator fun invoke(params: Params): Flow<Type> =
        execute(params).catch { e ->
            e.printStackTrace()
        }

    protected abstract fun execute(params: Params): Flow<Type>
}