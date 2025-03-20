package com.aditya.passwordmanagerdemo.domain.repo

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.aditya.passwordmanagerdemo.data.db.PasswordDao
import com.aditya.passwordmanagerdemo.domain.models.PasswordInfo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class PasswordRepo @Inject constructor(private val passwordDao: PasswordDao) {

    suspend fun insertPassword(passwordInfo: PasswordInfo) = passwordDao.insertPassword(passwordInfo)


    suspend fun updatePassword(passwordInfo: PasswordInfo) = passwordDao.updatePassword(passwordInfo)


    suspend fun deletePassword(passwordInfo: PasswordInfo) = passwordDao.deletePassword(passwordInfo)

    fun getAllPasswords() = passwordDao.getAllPasswords()
}