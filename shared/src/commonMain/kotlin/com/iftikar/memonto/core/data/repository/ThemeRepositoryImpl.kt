package com.iftikar.memonto.core.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.iftikar.memonto.core.domain.repository.ThemeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class ThemeRepositoryImpl(
    private val dataStore: DataStore<Preferences>
) : ThemeRepository {
    val DARK_MODE = booleanPreferencesKey("dark_mode")
    override fun isInDarkTheme(): Flow<Boolean?> {
        return dataStore.data.map { preferences ->
            preferences[DARK_MODE]
        }.catch {
            it.printStackTrace()
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun toggleDarkTheme() {
        dataStore.edit { preferences ->
            val current = preferences[DARK_MODE] ?: false
            preferences[DARK_MODE] = !current
        }
    }
}