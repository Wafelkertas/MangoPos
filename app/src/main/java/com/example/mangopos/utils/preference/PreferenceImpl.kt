package com.example.mangopos.utils.preference

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

val Context.dataStore:DataStore<Preferences> by preferencesDataStore(name = "mangopos")

@Singleton
class PreferenceImpl @Inject constructor(@ApplicationContext context: Context) : PreferencesStorage {

    private val dataStore = context.dataStore

    private object PreferencesKeys{
        val SAVED_KEY = stringPreferencesKey("saved_key")
    }

    override fun savedKey() = dataStore.data.catch { it ->
        if (it is IOException){
            emit(emptyPreferences())
        } else {
            throw it
        }
    }.map {
        it[PreferencesKeys.SAVED_KEY] ?: ""
    }

    override suspend fun setSavedKey(accessToken: String) {
        dataStore.edit {
            it[PreferencesKeys.SAVED_KEY] = accessToken
        }
    }

}