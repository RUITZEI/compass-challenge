package com.ruitzei.compass.challenge.data.repository

import android.util.Log
import com.ruitzei.compass.challenge.data.database.entity.AboutEntity
import com.ruitzei.compass.challenge.data.datasource.LocalDataSource
import com.ruitzei.compass.challenge.data.datasource.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AboutRepository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) {

    fun getContent(): Flow<String> = flow {
        localDataSource.getContent()?.let {
            emit(it.content)
        } ?: run {
            val content = getAndSaveContent()
            emit(content)
        }
    }

    private suspend fun getAndSaveContent(): String {
        val response = remoteDataSource.getContent()
        saveResponse(response)
        return response
    }

    private suspend fun saveResponse(content: String) {
        localDataSource.saveContent(
            AboutEntity(
                content = content,
                updatedAt = System.currentTimeMillis() / 1000
            )
        )
    }
}