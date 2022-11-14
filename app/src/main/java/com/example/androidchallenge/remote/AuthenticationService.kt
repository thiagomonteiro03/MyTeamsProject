package com.example.androidchallenge.remote
import com.example.androidchallenge.remote.retrofit.LoginRequest
import com.example.androidchallenge.remote.retrofit.TokenResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthenticationService {

    @POST("/auth")
    suspend fun auth(@Body loginRequest: LoginRequest): Response<TokenResponse>

}