package com.manyacov.data.avito_player.datasource.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "albums")
data class AlbumEntity(
    @PrimaryKey val albumId: Int,
//    val title: String,
//    val cover: String,
//    val coverSmall: String,
//    val coverMedium: String,
//    val coverBig: String,
//    val coverXl: String,
//    val md5Image: String,
//    val tracklist: String,
//    val type: String
)