package com.manyacov.feature_api_tracks.di

import com.manyacov.feature_api_tracks.presentation.ApiPlaylistViewModel
import dagger.Component

@Component(
    modules = [ApiPlaylistScreenModule::class]
)
@ApiPlaylistScope
interface ApiPlaylistScreenComponent {

    @Component.Builder
    interface Builder {
        fun build(): ApiPlaylistScreenComponent
    }

    fun getViewModel() : ApiPlaylistViewModel
}