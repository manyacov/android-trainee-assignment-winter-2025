package com.manyacov.common.domain

abstract class BaseUseCase<out Type, in Params> where Type : Any {
    open suspend operator fun invoke(params: Params): Type {
        return execute(params)
    }

    protected abstract suspend fun execute(params: Params): Type
}