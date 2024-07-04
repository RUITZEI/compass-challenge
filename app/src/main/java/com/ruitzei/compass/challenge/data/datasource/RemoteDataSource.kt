package com.ruitzei.compass.challenge.data.datasource

import com.ruitzei.compass.challenge.data.network.paths.About
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    val httpClient: HttpClient
) {
    suspend fun getContent(): String {
        return httpClient.get<About>(About()).body<String>()
    }
}