package com.example.androidchallenge.repository

import com.example.androidchallenge.model.TeamEntity
import com.example.androidchallenge.remote.TeamService
import retrofit2.Response
import javax.inject.Inject


class TeamRepository @Inject constructor(
    private val service: TeamService
) {

    suspend fun getTeams(): Response<List<TeamEntity>> {
        return service.getTeams()
    }

}