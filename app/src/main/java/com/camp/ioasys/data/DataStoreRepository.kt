package com.camp.ioasys.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.core.stringPreferencesKey
import com.camp.ioasys.models.User
import com.camp.ioasys.util.Constants.Companion.ACCESS_TOKEN
import com.camp.ioasys.util.Constants.Companion.CLIENT
import com.camp.ioasys.util.Constants.Companion.PREFERENCES_NAME
import com.camp.ioasys.util.Constants.Companion.UID
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore(PREFERENCES_NAME)

@ViewModelScoped
class DataStoreRepository @Inject constructor(@ApplicationContext private val context: Context) {

    private object PreferencesKeys {
        val accessToken = stringPreferencesKey(ACCESS_TOKEN)
        val client = stringPreferencesKey(CLIENT)
        val uid = stringPreferencesKey(UID)
    }

    private val dataStore: DataStore<Preferences> = context.dataStore

    suspend fun saveUserInfo(accessToken: String, client: String, uid: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.accessToken] = accessToken
            preferences[PreferencesKeys.client] = client
            preferences[PreferencesKeys.uid] = uid
        }
    }

    val readUserInfo: Flow<User> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val savedAccessToken = preferences[PreferencesKeys.accessToken] ?: ""
            val savedClient = preferences[PreferencesKeys.client] ?: ""
            val savedUid = preferences[PreferencesKeys.uid] ?: ""
            User(
                savedAccessToken,
                savedClient,
                savedUid
            )
        }

}