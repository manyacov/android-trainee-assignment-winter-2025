package com.manyacov.data.avito_player.datasource.remote.api

import com.manyacov.data.avito_player.datasource.remote.model.ApiPlaylistResponse
import com.manyacov.data.avito_player.datasource.remote.model.ApiSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PlaylistApi {

    @GET("chart")
    suspend fun getChartList(): Result<ApiPlaylistResponse>

    @GET("search")
    suspend fun getSearchedList(
        @Query("q") search: String,
        @Query("index") index: Int
    ): Result<ApiSearchResponse>
}