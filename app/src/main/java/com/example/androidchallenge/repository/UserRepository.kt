package com.example.androidchallenge.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.androidchallenge.Constants
import com.example.androidchallenge.model.UserEntity
import com.example.androidchallenge.remote.UserService
import retrofit2.Response
import javax.inject.Inject


class UserRepository @Inject constructor(
    private val service: UserService
) {

    suspend fun getUsers(token: String): Response<UserEntity> {
       return service.getUser(mapOf(Constants.TOKEN_HEADER to token))
    }

}