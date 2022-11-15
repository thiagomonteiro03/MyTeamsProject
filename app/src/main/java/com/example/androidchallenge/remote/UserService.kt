package com.example.androidchallenge.remote
import com.example.androidchallenge.Constants
import com.example.androidchallenge.model.UserEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface UserService {

    @GET("/login")
    suspend fun getUser(@Header(Constants.TOKEN_HEADER) token: String): Response<UserEntity>

}