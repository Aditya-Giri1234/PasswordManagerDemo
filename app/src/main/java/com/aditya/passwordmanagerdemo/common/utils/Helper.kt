package com.aditya.passwordmanagerdemo.common.utils

import android.content.Context
import android.widget.Toast

object Helper {
    private var toast : Toast? = null

    fun showToast(context : Context, message : String, duration : Int = Toast.LENGTH_SHORT){
        toast?.cancel()
        toast  = Toast.makeText(context, message, duration)
        toast?.show()
    }
}