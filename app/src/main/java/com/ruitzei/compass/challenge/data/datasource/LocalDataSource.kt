package com.ruitzei.compass.challenge.data.datasource

import com.ruitzei.compass.challenge.data.database.dao.AboutDao
import com.ruitzei.compass.challenge.data.database.entity.AboutEntity
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val dao: AboutDao
) {
    suspend fun getContent(): AboutEntity? = dao.getLatestAbout()

    suspend fun saveContent(aboutEntity: AboutEntity) {
        dao.clearEntries()
        dao.saveAbout(aboutEntity)
    }

    fun evictCache() {}
    fun isCacheClean(): Boolean = true
}