package com.example.rightdeedtest.ui.login

import androidx.lifecycle.ViewModel
import com.example.rightdeedtest.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
): ViewModel() {
    private val _uiState: MutableStateFlow<LoginUiState> = MutableStateFlow(LoginUiState.Normal())
    val uiState: StateFlow<LoginUiState> = _uiState

    fun updatePhoneTextField(newValue: String) {
        when(val uiStateValue = _uiState.value) {
            is LoginUiState.Normal -> {
                _uiState.value = uiStateValue.copy(phoneTextField = newValue)
            }
        }
    }
}