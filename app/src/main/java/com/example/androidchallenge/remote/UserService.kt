package com.example.androidchallenge.remote
import com.example.androidchallenge.model.UserEntity
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET

interface UserService {

    @GET("/login")
    suspend fun getUser(@Body secrets: Map<String, String>): Response<UserEntity>

}