package com.example.androidchallenge.di

import android.content.Context
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import com.example.androidchallenge.util.NetworkInfo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesDatastore(@ApplicationContext context: Context) =
        PreferenceDataStoreFactory.create {
            context.preferencesDataStoreFile("androidChallenge")
        }

    @Provides
    @Singleton
    fun providesResources(@ApplicationContext context: Context) = context.resources

    @Provides
    @Singleton
    fun providesNetworkInfo(@ApplicationContext context: Context) = NetworkInfo(context)

}