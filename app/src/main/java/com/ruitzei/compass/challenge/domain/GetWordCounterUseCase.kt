package com.ruitzei.compass.challenge.domain

import com.ruitzei.compass.challenge.data.repository.AboutRepository
import com.ruitzei.compass.challenge.domain.mapper.CounterMapper
import javax.inject.Inject

class GetWordCounterUseCase @Inject constructor(
    private val repository: AboutRepository,
    private val defaultMapper: CounterMapper
) : HomeworkUseCase<Pair<String, Int>>(repository, defaultMapper)