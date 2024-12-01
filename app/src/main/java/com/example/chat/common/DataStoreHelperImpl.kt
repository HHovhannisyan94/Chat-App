package com.example.chat.common

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.chat.domain.model.user.User
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class DataStoreHelperImpl(private val context: Context) : DataStoreHelper {

    private val Context.dataStore by preferencesDataStore(name = DATA_STORE_PREFS)

    override suspend fun saveUser(user: User) {
        val jsonString = Json.encodeToString(user)
        context.dataStore.edit { prefs ->
            prefs[USER_KEY] = jsonString
        }
    }

    override suspend fun getUser(): User? {
        val jsonString: String? =
            context.dataStore.data
                .map { prefs -> prefs[USER_KEY] }
                .first()
        return jsonString?.let { Json.decodeFromString<User>(it) }
    }

    override suspend fun clearSession() {
        context.dataStore.edit { it.clear() }
    }

    companion object {
        private const val DATA_STORE_PREFS = "DATA_STORE_PREFS"
        private val USER_KEY = stringPreferencesKey("USER")
    }
}