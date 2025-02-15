package com.manyacov.data.di

import com.manyacov.data.avito_player.repository.LocalPlaylistRepositoryImpl
import com.manyacov.data.avito_player.repository.PlaylistRepositoryImpl
import com.manyacov.domain.avito_player.repository.LocalPlaylistRepository
import com.manyacov.domain.avito_player.repository.PlaylistRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindPlaylistRepository(playlistRepositoryImpl: PlaylistRepositoryImpl): PlaylistRepository

    @Binds
    @Singleton
    abstract fun bindLocalPlaylistRepository(localPlaylistRepositoryImpl: LocalPlaylistRepositoryImpl): LocalPlaylistRepository
}