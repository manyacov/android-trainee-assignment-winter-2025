package com.manyacov.data.avito_player.datasource.remote.api

import com.manyacov.data.avito_player.datasource.remote.model.ApiSearchResponse
import com.manyacov.data.avito_player.datasource.remote.model.TrackDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PlaylistApi {

    @GET("track/{id}")
    suspend fun loadTrack(
        @Path("id") trackId: String
    ): Result<TrackDto>

    @GET("chart/0/tracks")
    suspend fun fetchChartTracks(
        //index - is offset
        @Query("index") index: Int,
        @Query("limit") limit: Int
    ): Response<ApiSearchResponse>

    @GET("search")
    suspend fun fetchSearchedList(
        @Query("q") search: String,
        //index - is offset
        @Query("index") index: Int,
        @Query("limit") limit: Int
    ): Response<ApiSearchResponse>
}