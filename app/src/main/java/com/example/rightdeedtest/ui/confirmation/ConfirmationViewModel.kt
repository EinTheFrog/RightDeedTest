package com.example.rightdeedtest.ui.confirmation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rightdeedtest.repository.LoginRepository
import com.example.rightdeedtest.ui.result.ResultUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConfirmationViewModel @Inject constructor(
    private val repository: LoginRepository
): ViewModel() {
    private val _uiState: MutableStateFlow<ConfirmationUiState> =
        MutableStateFlow(ConfirmationUiState.Normal())
    val uiState: StateFlow<ConfirmationUiState> = _uiState

    fun login(phone: String) {
        viewModelScope.launch(Dispatchers.IO) {
            startLoading()
            setPhone(phone)
            fetchCode(phone)
            finishLoading()
        }
    }

    fun updateCodeTextField(newValue: String) {
        when(val uiStateValue = _uiState.value) {
            is ConfirmationUiState.Normal -> {
                _uiState.value = uiStateValue.copy(codeTextField = newValue)
            }
        }
    }

    private fun setPhone(phone: String) {
        when (val currentState = _uiState.value) {
            is ConfirmationUiState.Normal -> _uiState.value = currentState.copy(phone = phone)
        }
    }

    private suspend fun fetchCode(phone: String) {
        val response = repository.login(phone)
        if (response.isSuccess) {
            val code = response.getOrNull()?.code
            _uiState.value = ConfirmationUiState.Normal(
                loading = _uiState.value.loading,
                code = code
            )
        } else {
            val error = response.exceptionOrNull()
            _uiState.value = ConfirmationUiState.Normal(errorMessage = error?.message)
        }
    }

    private fun startLoading() {
        when (val currentUiState = _uiState.value) {
            is ConfirmationUiState.Normal -> {
                _uiState.value = currentUiState.copy(loading = true)
            }
        }
    }

    private fun finishLoading() {
        when (val currentUiState = _uiState.value) {
            is ConfirmationUiState.Normal -> {
                _uiState.value = currentUiState.copy(loading = false)
            }
        }
    }
}