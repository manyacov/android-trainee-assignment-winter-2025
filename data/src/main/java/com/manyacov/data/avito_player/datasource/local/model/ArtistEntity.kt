package com.manyacov.data.avito_player.datasource.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "artists")
data class ArtistEntity(
    @PrimaryKey val artistId: Int,
//    val name: String,
//    val link: String,
//    val picture: String,
//    val pictureSmall: String,
//    val pictureMedium: String,
//    val pictureBig: String,
//    val pictureXl: String,
//    val radio: Boolean,
//    val tracklist: String,
//    val type: String
)
