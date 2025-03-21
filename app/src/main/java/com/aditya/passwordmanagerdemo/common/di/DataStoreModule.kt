package com.aditya.passwordmanagerdemo.common.di
import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

private const val DATASTORE_NAME = "app_preferences"

val Context.dataStore by preferencesDataStore(name = DATASTORE_NAME)

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Singleton
    @Provides
    fun provideDataStore(@ApplicationContext context: Context) = context.dataStore
}
