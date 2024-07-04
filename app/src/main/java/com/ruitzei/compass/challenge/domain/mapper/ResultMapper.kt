package com.ruitzei.compass.challenge.domain.mapper

interface ResultMapper<T> {
    fun mapResult(content: String): List<T>
}