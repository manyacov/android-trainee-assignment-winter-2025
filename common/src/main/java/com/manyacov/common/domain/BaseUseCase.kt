package com.manyacov.common.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch

abstract class BaseUseCase<out Type, in Params> where Type : Any {
    open suspend operator fun invoke(params: Params): Type {
        return execute(params)
    }

    protected abstract suspend fun execute(params: Params): Type
}