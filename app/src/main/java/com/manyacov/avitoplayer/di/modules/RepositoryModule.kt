package com.manyacov.avitoplayer.di.modules

import com.manyacov.data.avito_player.repository.PlaylistRepositoryImpl
import com.manyacov.domain.avito_player.repository.PlaylistRepository
import dagger.Binds
import dagger.Module
import dagger.Reusable

@Module
interface RepositoryModule {

    @Binds
    @Reusable
    fun bindPlaylistRepository(playlistRepositoryImpl: PlaylistRepositoryImpl): PlaylistRepository

}