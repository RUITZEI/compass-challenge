package com.ruitzei.compass.challenge.utils

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher

/**
 * Gets all emitted values of a [StateFlow] with a timeout (default 4 seconds).
 *
 * Use this extension from host-side (JVM) tests. It's recommended to use it alongside
 * `InstantTaskExecutorRule` or a similar mechanism to execute tasks synchronously.
 */
@ExperimentalCoroutinesApi
suspend fun <T> StateFlow<T>.awaitValues(
    advanceTimeInMillis: Long = 4_000L
): List<T> = with(TestScope(UnconfinedTestDispatcher())) {
    val results = mutableListOf<T>()
    advanceTimeBy(advanceTimeInMillis)
    val jobs = launch {
        this@awaitValues.toList(results)
    }
    try {
        jobs.cancel()
    } catch (e: Exception) {
    }
    results
}

/**
 * Gets the last emitted value of a [StateFlow] with a timeout (default 4 seconds).
 *
 * Use this extension from host-side (JVM) tests. It's recommended to use it alongside
 * `InstantTaskExecutorRule` or a similar mechanism to execute tasks synchronously.
 */
@ExperimentalCoroutinesApi
suspend fun <T> StateFlow<T>.awaitLastValue(advanceTimeInMillis: Long = 4_000L): T {
    return awaitValues(advanceTimeInMillis).last()
}

@ExperimentalCoroutinesApi
fun TestScope.advanceTimeBy(advanceTimeInMillis: Long) {
    this.testScheduler.apply { advanceTimeBy(advanceTimeInMillis); runCurrent() }
}
