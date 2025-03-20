package com.aditya.passwordmanagerdemo.common.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.aditya.passwordmanagerdemo.data.db.PasswordDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DBModule {

    @Provides
    @Singleton
    fun getRoom(@ApplicationContext context : Context) : PasswordDatabase{
        return Room.databaseBuilder(context, PasswordDatabase::class.java, "password_db").fallbackToDestructiveMigrationFrom().build()
    }

    @Provides
    @Singleton
    fun providePasswordDao(passwordDatabase: PasswordDatabase) = passwordDatabase.passwordDao()
}