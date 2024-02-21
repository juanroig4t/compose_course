package com.juanroig.composecourse.data.datasource.local.datastore.common

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AppDataStore(
    private val dataStore: DataStore<Preferences>
) {

    @Suppress("SameParameterValue")
    internal fun <T> getValueFlow(
        key: Preferences.Key<T>,
        defaultValue: T
    ): Flow<T> {
        return dataStore.data.map { preferences ->
            preferences[key] ?: defaultValue
        }
    }

    internal suspend fun <T> setValue(
        key: Preferences.Key<T>,
        value: T
    ) {
        dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    internal suspend fun <T> delete(key: Preferences.Key<T>) {
        dataStore.edit { preferences ->
            preferences.remove(key = key)
        }
    }
}
