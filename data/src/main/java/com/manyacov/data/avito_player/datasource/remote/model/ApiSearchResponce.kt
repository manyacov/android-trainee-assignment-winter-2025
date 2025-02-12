package com.manyacov.data.avito_player.datasource.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class ApiSearchResponse(
    val data: List<TrackDto>,
    val total: Int,
    val next: String
)