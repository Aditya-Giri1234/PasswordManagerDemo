package com.aditya.passwordmanagerdemo.common.di

import android.content.Context
import androidx.room.Room
import com.aditya.passwordmanagerdemo.common.utils.Constants
import com.aditya.passwordmanagerdemo.data.db.PasswordDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.runBlocking
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DBModule {

    @Provides
    @Singleton
    fun providePasswordDatabase(
        @ApplicationContext context: Context
    ): PasswordDatabase {
        val passphrase: ByteArray = runBlocking {
            Constants.PASS_PHRASE.toByteArray()
        }
        val factory = SupportFactory(passphrase)

        return Room.databaseBuilder(context, PasswordDatabase::class.java, "password_db")
            .openHelperFactory(factory)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun providePasswordDao(passwordDatabase: PasswordDatabase) = passwordDatabase.passwordDao()
}
