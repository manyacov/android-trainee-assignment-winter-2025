package com.manyacov.data.avito_player.datasource.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class ArtistDto(
    val id: Int,
    val name: String,
    val link: String,
    val picture: String,
    val pictureSmall: String,
    val pictureMedium: String,
    val pictureBig: String,
    val pictureXl: String,
    val radio: Boolean,
    val tracklist: String,
    val type: String
)