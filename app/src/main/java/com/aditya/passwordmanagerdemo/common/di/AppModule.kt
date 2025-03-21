package com.aditya.passwordmanagerdemo.common.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.aditya.passwordmanagerdemo.common.encryption.AESManager
import com.aditya.passwordmanagerdemo.data.SessionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideSessionManager(datastore: DataStore<Preferences>): SessionManager {
        return SessionManager(datastore)
    }

    @Provides
    fun provideAesManager(sessionManager: SessionManager): AESManager {
        return AESManager(sessionManager)
    }

}