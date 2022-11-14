package com.example.androidchallenge.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TeamEntity(
    val name: String,
    val city: String,
    val conference: String,
    val teamImageUrl: String,
    val description: String
) : Parcelable