package com.ruitzei.compass.challenge.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Calendar
import java.util.Date

@Entity(tableName = "about")
data class AboutEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "content") val content: String = "",
    @ColumnInfo(name = "updatedAt") val updatedAt: Long = System.currentTimeMillis()/1000
)