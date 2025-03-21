package com.aditya.passwordmanagerdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.aditya.passwordmanagerdemo.ui.navigation.AppNavigation
import com.aditya.passwordmanagerdemo.ui.screens.HomeScreen
import com.aditya.passwordmanagerdemo.ui.theme.PasswordManagerDemoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PasswordManagerDemoTheme {
                MyApp()
            }
        }
    }
}

@Composable
fun MyApp() {
    AppNavigation()
}