package com.aditya.passwordmanagerdemo.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor(private val dataStore: DataStore<Preferences>) {

    companion object {
        private val SESSION_PIN = stringPreferencesKey("Session_Pin")
        private val ENCRYPTED_PASSPHRASE_KEY = stringPreferencesKey("EncryptedPassphrase")
        private val AES_KEY_ALIAS = stringPreferencesKey("AesKey")
    }

    suspend fun setSessionPin(pin: String) {
        dataStore.edit { preferences ->
            preferences[SESSION_PIN] = pin
        }
    }

    suspend fun getSessionPin(): String? {
        return dataStore.data.map { preferences ->
            preferences[SESSION_PIN]
        }.first()
    }


    // --- AES Key (Example purpose) ---

    suspend fun getOrCreateAesKey(): String {
        val aesKey = dataStore.data.map { preferences ->
            preferences[AES_KEY_ALIAS]
        }.first()

        return if (aesKey != null) {
            aesKey
        } else {
            val randomAesKey = UUID.randomUUID().toString().take(16) // 16-char AES key
            dataStore.edit { preferences ->
                preferences[AES_KEY_ALIAS] = randomAesKey
            }
            randomAesKey
        }
    }
}
