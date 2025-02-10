package com.manyacov.feature_downloaded_tracks.di

import com.manyacov.feature_downloaded_tracks.presentation.DownloadedViewModel
import dagger.Component

@Component(
    modules = [DownloadedScreenModule::class]
)
@DownloadedScreenScope
interface DownloadedScreenComponent {

    @Component.Builder
    interface Builder {
        fun build(): DownloadedScreenComponent
    }

    fun getViewModel() : DownloadedViewModel
}