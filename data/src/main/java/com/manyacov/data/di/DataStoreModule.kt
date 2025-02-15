package com.manyacov.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.manyacov.data.avito_player.datasource.local.AvitoPlayerDatabase
import com.manyacov.data.avito_player.datasource.local.dao.AvitoPlayerDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "registration_session")

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }

    @Provides
    @Singleton
    fun provideRateTrackerDatabase(
        @ApplicationContext context: Context
    ): AvitoPlayerDatabase {
        return AvitoPlayerDatabase(context)
    }

    @Provides
    fun provideUserDao(database: AvitoPlayerDatabase): AvitoPlayerDao {
        return database.avitoPlayerDao
    }
}