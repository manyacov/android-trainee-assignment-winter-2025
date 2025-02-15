package com.manyacov.data.avito_player.datasource.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "playlist_table")
data class PlaylistInfoEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val path: String
)
