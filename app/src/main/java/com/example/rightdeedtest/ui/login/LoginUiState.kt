package com.example.rightdeedtest.ui.login

sealed interface LoginUiState {
    val loading: Boolean
    val errorMessage: String?

    data class Normal(
        override val loading: Boolean = false,
        override val errorMessage: String? = null,
        val phoneTextField: String = "",
    ): LoginUiState
}