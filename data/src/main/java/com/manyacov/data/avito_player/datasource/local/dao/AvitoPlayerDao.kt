package com.manyacov.data.avito_player.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.manyacov.data.avito_player.datasource.local.model.PlaylistInfoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AvitoPlayerDao {

    @Insert
    suspend fun saveCurrentPlaylist(list: List<PlaylistInfoEntity>)

    @Query("SELECT * FROM playlist_table")
    fun getCurrentPlaylistInfo(): Flow<List<PlaylistInfoEntity>>

    @Query("DELETE FROM playlist_table")
    suspend fun clearPlaylistTable()
}