package com.example.mangopos.utils.preference

import kotlinx.coroutines.flow.Flow

interface PreferencesStorage {
    fun savedKey(): Flow<String>
    suspend fun setSavedKey(accessToken : String)
}