package com.ruitzei.compass.challenge.domain.mapper

import javax.inject.Inject

class TenthWordMapper @Inject constructor(): ResultMapper<String> {
    override fun mapResult(content: String): List<String> {
        // Even though it's 0-indexed, I'm assuming we should return the index #10 just for simplicity
        var i = 0
        val response = mutableListOf<String>()

        while (i + 10 < content.length) {
            i+= 10
            response.add(content[i].toString())
        }

        return response
    }
}