package com.aditya.passwordmanagerdemo.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aditya.passwordmanagerdemo.common.model.ApiResponse
import com.aditya.passwordmanagerdemo.common.utils.Constants
import com.aditya.passwordmanagerdemo.common.utils.Helper
import com.aditya.passwordmanagerdemo.domain.models.PasswordInfo
import com.aditya.passwordmanagerdemo.ui.theme.floatColor
import com.aditya.passwordmanagerdemo.ui.viewmodels.HomeViewModel
import com.aditya.passwordmanagerdemo.ui.widgets.PasswordItem
import com.aditya.passwordmanagerdemo.ui.widgets.PasswordManagerBottomSheet
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun HomeScreen(homeViewModel: HomeViewModel = hiltViewModel()) {

    val sheetState = rememberModalBottomSheetState()

    var isBottomSheetOpen by remember {
        mutableStateOf(false)
    }

    var selectedPasswordInfo by rememberSaveable { mutableStateOf<PasswordInfo?>(null) }


    val dbOperation by homeViewModel.dbOperation.collectAsStateWithLifecycle(ApiResponse.Initial())
    val passwords by homeViewModel.passwords.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        homeViewModel.getAllPasswords()
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
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    selectedPasswordInfo = null
                    isBottomSheetOpen = true
                },
                containerColor = floatColor
            ) {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = "",
                    tint = Color.White,
                    modifier = Modifier.size(35.dp)
                )
            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            HorizontalDivider()
            if (passwords.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "No passwords found")
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 15.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(passwords) { password ->
                        PasswordItem(
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp),
                            password,
                            onItemClick = {
                                selectedPasswordInfo = it
                                isBottomSheetOpen = true
                            })
                    }
                }
            }
        }

    }


    PasswordManagerBottomSheet(
        Modifier.fillMaxWidth(),
        sheetState,
        isBottomSheetOpen,
        selectedPasswordInfo,
        onDismiss = {
            isBottomSheetOpen = false
        },
        onSave = {
            homeViewModel.insertPassword(it)
        },
        onEdit = {
            homeViewModel.updatePassword(it)
        },
        onDelete = {
            homeViewModel.deletePassword(it)
        }
    )

    DbOperation(Modifier.fillMaxSize(), dbOperation, sheetState, onBottomSheetDismiss = {
        isBottomSheetOpen = false
    })

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DbOperation(
    modifier: Modifier = Modifier,
    dbOperation: ApiResponse<String>,
    sheetState: SheetState,
    onBottomSheetDismiss: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    when (dbOperation) {
        is ApiResponse.Initial -> {}
        is ApiResponse.Success -> {
            Helper.showToast(context, dbOperation.data)
            LaunchedEffect(Unit) {
                scope.launch {
                    sheetState.hide()
                }.invokeOnCompletion {
                    if (!sheetState.isVisible) {
                        onBottomSheetDismiss()
                    }
                }
            }
        }

        is ApiResponse.Loading -> {
            Box(
                modifier = modifier.background(Color.Gray.copy(alpha = .4f)),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator(
                    color = Color.White
                )
            }
        }

        is ApiResponse.Error -> {
            Helper.showToast(context, dbOperation.message)
        }
    }
}