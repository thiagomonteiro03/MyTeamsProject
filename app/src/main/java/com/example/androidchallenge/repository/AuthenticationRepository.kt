package com.example.androidchallenge.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.androidchallenge.Constants
import com.example.androidchallenge.remote.retrofit.TokenResponse
import com.example.androidchallenge.remote.AuthenticationService
import com.example.androidchallenge.remote.retrofit.LoginRequest
import com.example.androidchallenge.extension.md5
import retrofit2.Response
import javax.inject.Inject


class AuthenticationRepository @Inject constructor(
    private val service: AuthenticationService,
    private val dataStore: DataStore<Preferences>
) {

    suspend fun authenticate(userName: String, password: String): Response<TokenResponse> {
        val response = service.auth(LoginRequest(userName, password))
        if (response.isSuccessful) saveCredentials(userName, password, response.body()?.token ?:"")
        return response
    }

    private suspend fun saveCredentials(userName: String, password: String, token: String) {
        dataStore.edit { pref ->
            pref[stringPreferencesKey(Constants.TOKEN_KEY)] = token
            pref[stringPreferencesKey(Constants.CLIENT_ID_KEY)] = userName
            pref[stringPreferencesKey(Constants.CLIENT_SECRET_KEY)] = password.md5
        }
    }

    suspend fun logout() {
        dataStore.edit { pref ->
            pref.clear()
        }
    }

}