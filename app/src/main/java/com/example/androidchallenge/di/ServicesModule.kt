package com.example.androidchallenge.di
import com.example.androidchallenge.remote.retrofit.ServiceBuilder
import com.example.androidchallenge.remote.AuthenticationService
import com.example.androidchallenge.remote.TeamService
import com.example.androidchallenge.remote.UserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServicesModule {

    @Provides
    @Singleton
    fun provideAuthenticationService(serviceBuilder: ServiceBuilder): AuthenticationService = serviceBuilder.buildService(AuthenticationService::class.java)

    @Provides
    @Singleton
    fun provideTeamService(serviceBuilder: ServiceBuilder): TeamService = serviceBuilder.buildService(TeamService::class.java)

    @Provides
    @Singleton
    fun provideUserService(serviceBuilder: ServiceBuilder): UserService = serviceBuilder.buildService(UserService::class.java)
}