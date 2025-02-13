package com.manyacov.data.di

import com.manyacov.data.avito_player.datasource.local.SessionCacheServiceImpl
import com.manyacov.domain.avito_player.services.SessionCacheService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SessionModule {

    @Binds
    @Singleton
    abstract fun bindSessionService(sessionCacheServiceImpl: SessionCacheServiceImpl): SessionCacheService
}