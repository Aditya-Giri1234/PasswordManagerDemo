package com.aditya.passwordmanagerdemo.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface AppNavigationScreen {

    @Serializable
    data object PinScreen : AppNavigationScreen

    @Serializable
    data object HomeScreen : AppNavigationScreen

}