package com.aditya.passwordmanagerdemo.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aditya.passwordmanagerdemo.ui.screens.HomeScreen
import com.aditya.passwordmanagerdemo.ui.screens.PinScreen


@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController , startDestination = AppNavigationScreen.PinScreen){
        composable<AppNavigationScreen.PinScreen> {
            PinScreen(hiltViewModel() , navController)
        }

        composable<AppNavigationScreen.HomeScreen> {
            HomeScreen(hiltViewModel())
        }
    }

}