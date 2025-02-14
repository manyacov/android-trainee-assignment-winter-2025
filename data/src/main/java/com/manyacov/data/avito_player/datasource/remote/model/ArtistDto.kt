package com.manyacov.data.avito_player.datasource.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class ArtistDto(
    val id: Int,
    val name: String,
    val type: String
)