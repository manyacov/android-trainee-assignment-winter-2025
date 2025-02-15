package com.manyacov.data.avito_player.datasource.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.manyacov.data.avito_player.datasource.local.dao.AvitoPlayerDao
import com.manyacov.data.avito_player.datasource.local.model.PlaylistInfoEntity

class AvitoPlayerDatabase internal constructor(private val database: AvitoPlayerRoomDatabase) {
    val avitoPlayerDao: AvitoPlayerDao
        get() = database.avitoPlayerDao()
}

@Database(
    entities = [PlaylistInfoEntity::class],
    version = 1,
    exportSchema = false
)

internal abstract class AvitoPlayerRoomDatabase : RoomDatabase() {

    abstract fun avitoPlayerDao(): AvitoPlayerDao
}

fun AvitoPlayerDatabase(applicationContext: Context): AvitoPlayerDatabase {
    val rateTrackerDatabase = Room.databaseBuilder(
        checkNotNull(applicationContext.applicationContext),
        AvitoPlayerRoomDatabase::class.java,
        "avito_player_db"
    ).build()
    return AvitoPlayerDatabase(rateTrackerDatabase)
}