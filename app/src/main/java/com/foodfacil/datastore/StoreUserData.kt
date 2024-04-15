package com.foodfacil.datastore
import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StoreUserData(private val context: Context) {
    // to make sure there is only one instance
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("UserData")

        val USER_EMAIL_KEY = stringPreferencesKey("user_email")
        val USER_NAME_KEY = stringPreferencesKey("user_name")
        val USER_UID = stringPreferencesKey("user_uid")
        val USER_TOKEN = stringPreferencesKey("user_token")
        val USER_PHOTO = stringPreferencesKey("user_photo")
    }

    val getEmail: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[USER_EMAIL_KEY] ?: ""
        }

    val getName: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[USER_NAME_KEY] ?: ""
        }


    val getUid: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[USER_UID] ?: ""
        }

    val getToken: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[USER_TOKEN] ?: ""
        }
    val getPhoto: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[USER_PHOTO] ?: ""
        }

    // to save the email
    suspend fun saveEmail(email: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_EMAIL_KEY] = email
            Log.e("TAG", "saveEmail: " )
        }
    }
    suspend fun saveName(name: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_NAME_KEY] = name
        }
    }

    suspend fun saveUid(uid: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_UID] = uid
        }
    }
    suspend fun saveToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_TOKEN] = token
        }
    }
    suspend fun savePhoto(photo: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_PHOTO] = photo
        }
    }

    suspend fun clearAllData() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }

}