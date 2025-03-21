package com.aditya.passwordmanagerdemo.common.utils

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.saveable.Saver
import com.aditya.passwordmanagerdemo.domain.models.PasswordInfo

object Helper {
    private var toast : Toast? = null

    fun showToast(context : Context, message : String, duration : Int = Toast.LENGTH_SHORT){
        toast?.cancel()
        toast  = Toast.makeText(context, message, duration)
        toast?.show()
    }
}

val passwordInfoSaver = Saver<PasswordInfo, Map<String, Any>>(
    save = { info ->
        mapOf(
            "id" to info.id,
            "passwordType" to info.passwordType,
            "password" to info.password,
            "userNameOrEmail" to info.userNameOrEmail
        )
    },
    restore = { map ->
        PasswordInfo(
            id = (map["id"] as Number).toInt(),
            passwordType = map["passwordType"] as String,
            password = map["password"] as String,
            userNameOrEmail = map["userNameOrEmail"] as String
        )
    }
)