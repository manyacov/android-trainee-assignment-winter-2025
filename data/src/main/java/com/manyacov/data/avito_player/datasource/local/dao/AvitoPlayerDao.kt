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

    @Query("SELECT * FROM playlist_table WHERE path = :path")
    suspend fun getTrackById(path: String): PlaylistInfoEntity?

    @Query("SELECT * FROM playlist_table WHERE id < :currentId ORDER BY id DESC LIMIT :limit")
    suspend fun getPreviousTwoTracks(currentId: Long, limit: Int): List<PlaylistInfoEntity>

    @Query("SELECT * FROM playlist_table WHERE id > :currentId ORDER BY id ASC LIMIT :limit")
    suspend fun getNextTwoTracks(currentId: Long, limit: Int): List<PlaylistInfoEntity>

    @Query("SELECT * FROM playlist_table WHERE id < :currentId - 2 ORDER BY id DESC LIMIT :limit")
    suspend fun getPreviousOne(currentId: Long?, limit: Int): PlaylistInfoEntity?

    @Query("SELECT * FROM playlist_table WHERE id > :currentId + 2 ORDER BY id ASC LIMIT :limit")
    suspend fun getNextOne(currentId: Long?, limit: Int): PlaylistInfoEntity?
}