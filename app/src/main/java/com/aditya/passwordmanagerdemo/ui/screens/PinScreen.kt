package com.aditya.passwordmanagerdemo.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.aditya.passwordmanagerdemo.common.utils.Constants
import com.aditya.passwordmanagerdemo.common.utils.Helper
import com.aditya.passwordmanagerdemo.ui.components.AddVerticalSpace
import com.aditya.passwordmanagerdemo.ui.navigation.AppNavigationScreen
import com.aditya.passwordmanagerdemo.ui.viewmodels.HomeViewModel
import com.aditya.passwordmanagerdemo.ui.widgets.pin.OtpTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PinScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    navController: NavController = rememberNavController()
) {

    var scope = rememberCoroutineScope()
    val context = LocalContext.current

    var pinValue by remember {
        mutableStateOf("")
    }
    var isAllPinFill by remember {
        mutableStateOf(false)
    }

    val sessionPin by homeViewModel.sessionPin.collectAsState()

    LaunchedEffect(pinValue) {
        if (isAllPinFill) {
            isAllPinFill = false

            when {
                sessionPin == null -> {
                    homeViewModel.setPin(pinValue)
                    navController.navigate(AppNavigationScreen.HomeScreen) {
                        popUpTo<AppNavigationScreen.PinScreen> {
                            inclusive = true
                        }
                    }
                }

                pinValue == sessionPin -> {
                    navController.navigate(AppNavigationScreen.HomeScreen) {
                        popUpTo<AppNavigationScreen.PinScreen> {
                            inclusive = true
                        }
                    }
                }

                else -> {
                    Helper.showToast(context, "Pin is invalid !")
                }
            }
        }
    }



    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        Constants.APP_TITLE, style = MaterialTheme.typography.titleLarge.copy(
                            color = Color.Black,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth()
                    .fillMaxHeight(.4f),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 4.dp
                ),
                shape = RoundedCornerShape(15.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    AddVerticalSpace(20)
                    Text(
                        if(sessionPin == null)"Create Pin" else "Enter Pin", style = MaterialTheme.typography.titleLarge.copy(
                            color = Color.Black, fontWeight = FontWeight.SemiBold
                        )
                    )
                    Box(
                        Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        OtpTextField(
                            otpText = pinValue
                        ) { value, isAllPinValueFilled ->
                            pinValue = value
                            isAllPinFill = isAllPinValueFilled
                        }
                    }
                }
            }
        }
    }
}