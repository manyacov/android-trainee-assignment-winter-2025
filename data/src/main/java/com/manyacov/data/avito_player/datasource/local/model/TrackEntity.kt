package com.manyacov.data.avito_player.datasource.local.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tracks")
data class TrackEntity(
    @PrimaryKey val id: Long,
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
    @Embedded val artist: ArtistEntity,
    @Embedded val album: AlbumEntity,
    val type: String
)
