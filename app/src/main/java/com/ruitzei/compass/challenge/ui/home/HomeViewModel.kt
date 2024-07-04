package com.ruitzei.compass.challenge.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ruitzei.compass.challenge.di.dispatcher.CoroutinesDispatcherProvider
import com.ruitzei.compass.challenge.domain.GetEveryTenthCharacterUseCase
import com.ruitzei.compass.challenge.domain.GetWordCounterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val tenthCharUseCase: GetEveryTenthCharacterUseCase,
    private val wordCounterUseCase: GetWordCounterUseCase,
    private val dispatcher: CoroutinesDispatcherProvider
) : ViewModel() {

    private val _leftScreenUiState: MutableStateFlow<HomeState> = MutableStateFlow(HomeState.Idle)
    val leftScreenUiState: StateFlow<HomeState> = _leftScreenUiState.asStateFlow()

    private val _rightScreenUiState: MutableStateFlow<HomeState> = MutableStateFlow(HomeState.Idle)
    val rightScreenUiState: StateFlow<HomeState> = _rightScreenUiState.asStateFlow()

    fun onButtonClicked() {
        viewModelScope.launch(dispatcher.io) {
            _leftScreenUiState.update { HomeState.Loading }
            _rightScreenUiState.update { HomeState.Loading }

            tenthCharUseCase()
                .catch { throwable ->
                    _leftScreenUiState.update { HomeState.Error(throwable.message.orEmpty()) }
                }
                .collect { result ->
                    _leftScreenUiState.update { HomeState.Success(result.joinToString()) }
                }

            wordCounterUseCase()
                .catch { throwable ->
                    _rightScreenUiState.update { HomeState.Error(throwable.message.orEmpty()) }
                }
                .collect { result ->
                    _rightScreenUiState.update {
                        HomeState.Success(result.joinToString(",\n") { "[ ${it.first} : ${it.second} ]" })
                    }
                }
        }
    }
}

sealed class HomeState {
    data object Loading : HomeState()
    data object Idle : HomeState()
    data class Success(val string: String) : HomeState()
    data class Error(val cause: String) : HomeState()
}