package com.manyacov.data.avito_player.datasource.remote.api

import com.manyacov.data.avito_player.datasource.remote.model.ApiPlaylistResponse
import retrofit2.Response
import retrofit2.http.GET

interface PlaylistApi {

    @GET("chart")
    suspend fun getChartList(): Response<ApiPlaylistResponse>
}