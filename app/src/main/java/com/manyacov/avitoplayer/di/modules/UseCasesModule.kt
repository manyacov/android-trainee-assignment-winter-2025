package com.manyacov.avitoplayer.di.modules

import com.manyacov.domain.avito_player.repository.PlaylistRepository
import com.manyacov.domain.avito_player.use_case.GetApiTracksUseCase
import dagger.Module
import dagger.Provides

@Module
object UseCasesModule {

    @Provides
    fun provideGetApiTracksUseCase(playlistRepository: PlaylistRepository): GetApiTracksUseCase =
        GetApiTracksUseCase(playlistRepository)
}