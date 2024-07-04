@file:OptIn(ExperimentalCoroutinesApi::class)

package com.ruitzei.compass.challenge

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ruitzei.compass.challenge.data.repository.AboutRepository
import com.ruitzei.compass.challenge.domain.GetEveryTenthCharacterUseCase
import com.ruitzei.compass.challenge.domain.GetWordCounterUseCase
import com.ruitzei.compass.challenge.domain.mapper.TenthWordMapper
import com.ruitzei.compass.challenge.ui.home.HomeState
import com.ruitzei.compass.challenge.ui.home.HomeViewModel
import com.ruitzei.compass.challenge.utils.awaitLastValue
import com.ruitzei.compass.challenge.utils.provideFakeCoroutinesDispatcherProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`


class ViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var tenthCharacterUseCase: GetEveryTenthCharacterUseCase
    private lateinit var counterUseCase: GetWordCounterUseCase

    @Before
    fun setUp() {
        tenthCharacterUseCase = mock()
        `when`(tenthCharacterUseCase.invoke()).thenReturn(flowOf(listOf()))

        counterUseCase = mock()
        `when`(counterUseCase.invoke()).thenReturn(flowOf(listOf()))
    }


    @Test
    fun `test viewmodel initial state`() = runTest {
        val repository: AboutRepository = mock()
        `when`(repository.getContent()).thenReturn(flowOf("0123456789A"))

        val viewModel: HomeViewModel = HomeViewModel(
            mock(),
            mock(),
            provideFakeCoroutinesDispatcherProvider()
        )

        val expected = HomeState.Idle
        Assert.assertEquals(expected, viewModel.leftScreenUiState.awaitLastValue())
        Assert.assertEquals(expected, viewModel.rightScreenUiState.awaitLastValue())
    }


    @Test
    fun `test states are Success when both usecases succeeded`() = runTest {
        val viewModel: HomeViewModel = HomeViewModel(
            tenthCharacterUseCase,
            counterUseCase,
            provideFakeCoroutinesDispatcherProvider()
        )

        viewModel.onButtonClicked()

        val expectedList = HomeState.Success("")
        val expectedListPair = HomeState.Success("")
        Assert.assertEquals(
            expectedList,
            viewModel.leftScreenUiState.awaitLastValue()
        )

        Assert.assertEquals(
            expectedListPair,
            viewModel.rightScreenUiState.awaitLastValue()
        )
    }

    @Test
    fun `test states is Error when Usecase throws exception`() = runTest {
        val repository: AboutRepository = mock()
        given(repository.getContent()).willAnswer { flow<String> { throw Exception("asd") } }

        val failingUseCase = GetEveryTenthCharacterUseCase(repository, TenthWordMapper())

        val viewModel: HomeViewModel = HomeViewModel(
            failingUseCase,
            counterUseCase,
            provideFakeCoroutinesDispatcherProvider()
        )

        viewModel.onButtonClicked()

        val errorState = HomeState.Error("asd")

        Assert.assertEquals(
            errorState,
            viewModel.leftScreenUiState.awaitLastValue()
        )
    }
}