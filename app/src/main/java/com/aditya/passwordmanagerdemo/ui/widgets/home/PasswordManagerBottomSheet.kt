package com.aditya.passwordmanagerdemo.ui.widgets.home

import android.content.Context
import android.util.Patterns
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aditya.passwordmanagerdemo.common.utils.Helper
import com.aditya.passwordmanagerdemo.domain.models.PasswordInfo
import com.aditya.passwordmanagerdemo.ui.components.AddHorizontalSpace
import com.aditya.passwordmanagerdemo.ui.components.AddVerticalSpace
import com.aditya.passwordmanagerdemo.ui.components.CustomButton
import com.aditya.passwordmanagerdemo.ui.components.FormTextField
import com.aditya.passwordmanagerdemo.ui.theme.floatColor

@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordManagerBottomSheet(
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(),
    isBottomSheetOpen: Boolean = true,
    passwordInfo: PasswordInfo? = null,
    onDismiss: () -> Unit = {},
    onSave: (PasswordInfo) -> Unit = {},
    onEdit: (PasswordInfo) -> Unit = {},
    onDelete: (PasswordInfo) -> Unit = {}
) {
    println(passwordInfo)
    var passwordType by remember(passwordInfo) {
        mutableStateOf(passwordInfo?.passwordType ?: "")
    }

    var userNameOrEmail by remember(passwordInfo) {
        mutableStateOf(passwordInfo?.userNameOrEmail ?: "")
    }

    var password by remember(passwordInfo) {
        mutableStateOf(passwordInfo?.password ?: "")
    }

    var strengthLabel by remember {
        mutableStateOf("")
    }
    var strengthColor by remember {
        mutableStateOf(Color.Transparent)
    }

    LaunchedEffect(password) {
        val value = evaluatePasswordStrength(password)
        strengthLabel = value.first
        strengthColor = value.second
    }


    val keyboardController = LocalSoftwareKeyboardController.current
    val focusController = LocalFocusManager.current
    val context = LocalContext.current


    if (isBottomSheetOpen) {
        ModalBottomSheet(
            sheetState = sheetState,
            modifier = modifier.systemBarsPadding().imePadding(),
            onDismissRequest = onDismiss,
            contentWindowInsets = {WindowInsets.safeDrawing},
            dragHandle = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        BottomSheetDefaults.DragHandle(color = Color.Gray)
                    }
                    AnimatedVisibility(
                        passwordInfo != null,
                        modifier = Modifier.padding(start = 20.dp),
                    ) {
                        Text(
                            "Account Details", style = MaterialTheme.typography.titleLarge.copy(
                                color = floatColor,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }
                }
            }
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AddVerticalSpace(10)
                FormTextField(
                    Modifier.fillMaxWidth(),
                    value = passwordType,
                    onValueChange = {
                        passwordType = it
                    },
                    labelText = "Password Type",
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    )
                )
                AddVerticalSpace(10)

                FormTextField(
                    Modifier.fillMaxWidth(),
                    value = userNameOrEmail,
                    onValueChange = {
                        userNameOrEmail = it
                    },
                    labelText = "Username / Email",
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    )
                )
                AddVerticalSpace(10)


                FormTextField(
                    Modifier.fillMaxWidth(),
                    value = password,
                    onValueChange = {
                        password = it
                    },
                    labelText = "Password",
                    isForPassword = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions {
                        keyboardController?.hide()
                        focusController.clearFocus(true)
                    }
                )
                // Show strength indicator only when password length >= 8
                if (password.length >= 8 && strengthLabel.isNotEmpty()) {
                    AddVerticalSpace(8)
                    Text(
                        text = "Password strength: $strengthLabel",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = strengthColor,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
                AddVerticalSpace(20)

                if (passwordInfo == null) {
                    CustomButton(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth(),
                        onClick = {
                            if (validateInputs(context, passwordType, userNameOrEmail, password)) {
                                onSave(
                                    PasswordInfo(
                                        passwordType = passwordType,
                                        userNameOrEmail = userNameOrEmail,
                                        password = password
                                    )
                                )
                            }
                        },
                        label = "Add New Account",
                        color = Color.Black
                    )
                } else {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CustomButton(
                            modifier = Modifier.weight(1f),
                            onClick = {
                                if (validateInputs(
                                        context,
                                        passwordType,
                                        userNameOrEmail,
                                        password
                                    )
                                ) {
                                    onEdit(
                                        PasswordInfo(
                                            id = passwordInfo.id,
                                            passwordType = passwordType,
                                            userNameOrEmail = userNameOrEmail,
                                            password = password
                                        )
                                    )
                                }
                            },
                            label = "Edit",
                            color = Color.Black
                        )
                        AddHorizontalSpace(10)
                        CustomButton(
                            modifier = Modifier.weight(1f),
                            onClick = {
                                onDelete(passwordInfo)
                            },
                            label = "Delete",
                            color = Color.Red.copy(
                                alpha = .8f
                            )
                        )
                    }
                }
            }
        }
    }

}

fun validateInputs(
    context: Context,
    accountType: String,
    username: String,
    password: String
): Boolean {
    return when {
        accountType.isBlank() || username.isBlank() || password.isBlank() -> {
            Helper.showToast(context, "All fields are required")
            false
        }

        '@' in username && !Patterns.EMAIL_ADDRESS.matcher(username).matches() -> {
            Helper.showToast(context, "Enter a valid email")
            false
        }

        password.length < 8 -> {
            Helper.showToast(context, "Password must be at least 8 characters long")
            false
        }

        else -> true // All validations passed
    }
}


/**
 * Evaluates the strength of a password based on character variety.
 * Returns a pair: [strengthLabel, strengthColor].
 */
fun evaluatePasswordStrength(password: String): Pair<String, Color> {
    if (password.length < 8) {
        // You may decide not to show any indicator if the length is below 8.
        return Pair("", Color.Transparent)
    }
    var score = 0
    // Increase score for presence of lower-case letters.
    if (password.any { it.isLowerCase() }) score++
    // Increase score for presence of upper-case letters.
    if (password.any { it.isUpperCase() }) score++
    // Increase score for presence of digits.
    if (password.any { it.isDigit() }) score++
    // Increase score for presence of special characters.
    if (password.any { !it.isLetterOrDigit() }) score++

    return when (score) {
        0, 1 -> Pair("Weak", Color.Red)
        2, 3 -> Pair("Medium", Color(0xFFFFA500)) // Orange
        4 -> Pair("Strong", Color.Green)
        else -> Pair("Weak", Color.Red)
    }
}