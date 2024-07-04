package com.ruitzei.compass.challenge.domain

import com.ruitzei.compass.challenge.data.repository.AboutRepository
import com.ruitzei.compass.challenge.domain.mapper.ResultMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

abstract class HomeworkUseCase<T> (
    private val repository: AboutRepository,
    private val mapper: ResultMapper<T>
) {
    operator fun invoke(): Flow<List<T>> {
        return repository.getContent().map {
            mapper.mapResult(it)
        }
    }
}