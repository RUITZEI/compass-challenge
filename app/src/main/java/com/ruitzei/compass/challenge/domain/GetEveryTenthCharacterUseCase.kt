package com.ruitzei.compass.challenge.domain

import com.ruitzei.compass.challenge.data.repository.AboutRepository
import com.ruitzei.compass.challenge.domain.mapper.TenthWordMapper
import javax.inject.Inject

class GetEveryTenthCharacterUseCase @Inject constructor(
    repository: AboutRepository,
    defaultMapper: TenthWordMapper
) : HomeworkUseCase<String>(repository, defaultMapper)