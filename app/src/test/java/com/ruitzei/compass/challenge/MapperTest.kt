package com.ruitzei.compass.challenge

import org.junit.Assert.*
import com.ruitzei.compass.challenge.domain.mapper.CounterMapper
import com.ruitzei.compass.challenge.domain.mapper.TenthWordMapper
import org.junit.Test

class MapperTest {
    @Test
    fun `test 10th word mapper`() {
        val mapper = TenthWordMapper()
        val testResult = "0123456789A012345678B012345678C"

        val mapResult = mapper.mapResult(testResult)
        assertEquals(listOf("A", "B", "C"), mapResult)
    }

    @Test
    fun `test 10th word mapper with empty string`() {
        val mapper = TenthWordMapper()
        val testResult = ""

        val mapResult = mapper.mapResult(testResult)
        assertEquals(emptyList<String>(), mapResult)
    }

    @Test
    fun `test word counter mapper`() {
        val mapper = CounterMapper()
        val testResult = "123 123 123"

        val mapResult = mapper.mapResult(testResult)
        assertEquals(listOf(Pair("123", 3)), mapResult)
    }

    @Test
    fun `test word counter mapper with empty string`() {
        val mapper = CounterMapper()
        val testResult = ""

        val mapResult = mapper.mapResult(testResult)
        assertEquals(emptyList<Pair<String, Int>>(), mapResult)
    }
}