package com.manyacov.data.avito_player.datasource.remote.api

import com.manyacov.data.avito_player.datasource.remote.model.ApiPlaylistResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PlaylistApi {

    @GET("chart")
    suspend fun getChartList(): Result<ApiPlaylistResponse>

    //https://api.deezer.com/search?q={query}.
    @GET("search")
    suspend fun getSearchedList(
        @Query("q") search: String
    ): Result<ApiPlaylistResponse>
}