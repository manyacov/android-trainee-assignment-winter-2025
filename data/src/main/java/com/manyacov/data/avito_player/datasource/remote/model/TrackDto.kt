package com.manyacov.data.avito_player.datasource.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class TrackDto(
    val id: Int,
    val title: String,
    val titleShort: String,
    val titleVersion: String,
    val link: String,
    val duration: Int,
    val rank: Int,
    val explicitLyrics: Boolean,
    val explicitContentLyrics: Int,
    val explicitContentCover: Int,
    val preview: String,
    val md5Image: String,
    val position: Int,
    val artist: ArtistDto,
    val album: AlbumDto,
    val type: String
)