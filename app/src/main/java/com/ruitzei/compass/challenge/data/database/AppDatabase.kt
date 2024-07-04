package com.ruitzei.compass.challenge.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ruitzei.compass.challenge.data.database.dao.AboutDao
import com.ruitzei.compass.challenge.data.database.entity.AboutEntity

@Database(entities = [AboutEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun provideAboutDao(): AboutDao
}