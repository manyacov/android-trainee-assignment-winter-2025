package com.manyacov.feature_downloaded_tracks.di

import com.manyacov.feature_downloaded_tracks.presentation.DownloadedViewModel
import dagger.Module
import dagger.Provides

@Module
class DownloadedScreenModule {

    @Provides
    @DownloadedScreenScope
    fun provideViewModel(): DownloadedViewModel = DownloadedViewModel()
}