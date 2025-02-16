package com.manyacov.data.avito_player.datasource.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class AlbumDto(
    val id: Int,
    val title: String,
    val cover: String,
    val cover_big: String,
    val type: String
)