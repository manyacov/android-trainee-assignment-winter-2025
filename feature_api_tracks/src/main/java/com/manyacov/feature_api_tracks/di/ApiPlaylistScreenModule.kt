package com.manyacov.feature_api_tracks.di

import com.manyacov.feature_api_tracks.presentation.ApiPlaylistViewModel
import dagger.Module
import dagger.Provides

@Module
class ApiPlaylistScreenModule {

    @Provides
    @ApiPlaylistScope
    fun provideViewModel(): ApiPlaylistViewModel = ApiPlaylistViewModel()
}