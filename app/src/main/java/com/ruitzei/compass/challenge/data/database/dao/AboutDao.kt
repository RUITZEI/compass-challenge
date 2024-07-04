package com.ruitzei.compass.challenge.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ruitzei.compass.challenge.data.database.entity.AboutEntity

@Dao
interface AboutDao {
    @Query("SELECT * FROM about LIMIT 1")
    suspend fun getLatestAbout(): AboutEntity?

    @Query("DELETE FROM about")
    suspend fun clearEntries()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAbout(aboutEntity: AboutEntity)
}