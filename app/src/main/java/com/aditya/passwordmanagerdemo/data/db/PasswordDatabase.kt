package com.aditya.passwordmanagerdemo.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aditya.passwordmanagerdemo.domain.models.PasswordInfo
import javax.inject.Singleton

@Database(entities = [PasswordInfo::class], version = 1, exportSchema = false)
@Singleton
abstract class PasswordDatabase : RoomDatabase(){
    abstract fun passwordDao() : PasswordDao
}