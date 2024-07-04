package com.ruitzei.compass.challenge.di

import android.content.Context
import androidx.room.Room
import com.ruitzei.compass.challenge.data.database.AppDatabase
import com.ruitzei.compass.challenge.data.database.dao.AboutDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    fun provideRoomDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java, "compass_challenge"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideAboutDao(appDatabase: AppDatabase): AboutDao {
        return appDatabase.provideAboutDao()
    }
}