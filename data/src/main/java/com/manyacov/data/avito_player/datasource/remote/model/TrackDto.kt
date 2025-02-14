package com.manyacov.data.avito_player.datasource.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class TrackDto(
    val id: Long,
    val title: String,
    val title_short: String,
    val duration: Int,
    val preview: String,
    val artist: ArtistDto,
    val album: AlbumDto,
    val type: String
)