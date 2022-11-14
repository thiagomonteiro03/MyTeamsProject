package com.example.androidchallenge.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserEntity(
    val name: String,
    val age: String,
    val gender: String
) : Parcelable