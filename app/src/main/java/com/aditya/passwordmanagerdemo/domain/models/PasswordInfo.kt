package com.aditya.passwordmanagerdemo.domain.models

import androidx.compose.ui.tooling.preview.Preview
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PasswordInfo(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val passwordType : String,
    val password : String,
    val userNameOrEmail : String
)
