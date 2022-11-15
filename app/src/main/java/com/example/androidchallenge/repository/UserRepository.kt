package com.example.androidchallenge.repository

import com.example.androidchallenge.model.UserEntity
import com.example.androidchallenge.remote.UserService
import retrofit2.Response
import javax.inject.Inject


class UserRepository @Inject constructor(
    private val service: UserService
) {

    suspend fun getUsers(token: String): Response<UserEntity> {
       return service.getUser(token)
    }

}