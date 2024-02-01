package com.example.rightdeedtest.ui.result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rightdeedtest.repository.TokenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(
    private val tokenRepository: TokenRepository,
): ViewModel() {
    private val _uiState: MutableStateFlow<ResultUiState> = MutableStateFlow(ResultUiState.Success())
    val uiState: StateFlow<ResultUiState> = _uiState

    fun getToken(phone: String, code: String) {
        viewModelScope.launch {
            startLoading()
            setPhone(phone)
            fetchToken(phone, code)
            finishLoading()
        }
    }

    private fun setPhone(phone: String) {
        when (val currentState = _uiState.value) {
            is ResultUiState.Success -> _uiState.value = currentState.copy(phone = phone)
            is ResultUiState.Failure -> _uiState.value = currentState.copy(phone = phone)
        }

    }

    private suspend fun fetchToken(phone: String, code: String) {
        val tokenResult = tokenRepository.getToken(phone, code)
        if (tokenResult.isSuccess) {
            val token = tokenResult.getOrThrow()
            _uiState.value = ResultUiState.Success(
                loading = _uiState.value.loading,
                phone = _uiState.value.phone,
                token = token,
            )
        } else {
            _uiState.value = ResultUiState.Failure()
        }
    }

    private fun startLoading() {
        when (val currentUiState = _uiState.value) {
            is ResultUiState.Success -> {
                _uiState.value = currentUiState.copy(loading = true)
            }
            is ResultUiState.Failure -> {
                _uiState.value = currentUiState.copy(loading = true)
            }
        }
    }

    private fun finishLoading() {
        when (val currentUiState = _uiState.value) {
            is ResultUiState.Success -> {
                _uiState.value = currentUiState.copy(loading = false)
            }
            is ResultUiState.Failure -> {
                _uiState.value = currentUiState.copy(loading = false)
            }
        }
    }
}