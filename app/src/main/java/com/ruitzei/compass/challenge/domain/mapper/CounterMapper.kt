package com.ruitzei.compass.challenge.domain.mapper

import javax.inject.Inject

class CounterMapper @Inject constructor() : ResultMapper<Pair<String, Int>> {
    override fun mapResult(content: String): List<Pair<String, Int>> {
        val dictionary = hashMapOf<String, Int>()

        content.split("\\s+".toRegex())
            .filter { it.isNotEmpty() }
            .forEach {
                dictionary[it] = dictionary.getOrPut(it) { 0 }.inc()
            }

        return dictionary.map { entry -> Pair(entry.key, entry.value) }.sortedByDescending { it.second }
    }
}