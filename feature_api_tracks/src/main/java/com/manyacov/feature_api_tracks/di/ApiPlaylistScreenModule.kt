package com.manyacov.feature_api_tracks.di

import com.manyacov.domain.avito_player.use_case.GetApiTracksUseCase
import com.manyacov.feature_api_tracks.presentation.ApiPlaylistViewModel
import dagger.Module
import dagger.Provides

@Module
class ApiPlaylistScreenModule {

    @Provides
    @ApiPlaylistScope
    fun provideViewModel(getApiTracksUseCase: GetApiTracksUseCase): ApiPlaylistViewModel =
        ApiPlaylistViewModel(getApiTracksUseCase)
}