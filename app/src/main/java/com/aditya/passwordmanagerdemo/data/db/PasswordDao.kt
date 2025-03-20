package com.aditya.passwordmanagerdemo.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.aditya.passwordmanagerdemo.domain.models.PasswordInfo
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Dao
@Singleton
interface PasswordDao {

    @Insert
    suspend fun insertPassword(passwordInfo: PasswordInfo) : Long

    @Update
    suspend fun updatePassword(passwordInfo: PasswordInfo) : Int

    @Delete
    suspend fun deletePassword(passwordInfo: PasswordInfo) : Int

    @Query("SELECT * FROM PasswordInfo")
     fun getAllPasswords() : Flow<List<PasswordInfo>>

}