package com.example.androidchallenge.remote
import com.example.androidchallenge.model.TeamEntity
import retrofit2.Response
import retrofit2.http.GET

interface TeamService {

    @GET("/teams")
    suspend fun getTeams(): Response<List<TeamEntity>>

}