package com.example.androidchallenge.extension

import androidx.annotation.IdRes
import androidx.annotation.NavigationRes
import androidx.navigation.NavController
import androidx.navigation.NavDirections

fun NavController.navigate(@IdRes @NavigationRes currentDestination: Int, directions: NavDirections) {
    if (this.currentDestination?.id == currentDestination) {
        navigate(directions)
    }
}