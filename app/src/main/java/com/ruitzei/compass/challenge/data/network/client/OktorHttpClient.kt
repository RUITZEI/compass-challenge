package com.ruitzei.compass.challenge.data.network.client

import io.ktor.client.HttpClient

interface OktorHttpClient {
    fun getHttpClient(): HttpClient
}