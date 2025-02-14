package com.manyacov.data.avito_player.datasource.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.manyacov.domain.avito_player.services.SessionCacheService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SessionCacheServiceImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
): SessionCacheService {
    private val TRACK_PATH_KEY = stringPreferencesKey("track_path")

    override suspend fun saveTrackPath(path: String) {
        dataStore.edit { preferences ->
            preferences[TRACK_PATH_KEY] = path
        }
    }

    override fun getTrackPath(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[TRACK_PATH_KEY]
        }
    }

    override suspend fun clearSession() {
        dataStore.edit { preferences ->
            preferences.remove(TRACK_PATH_KEY)
        }
    }

}