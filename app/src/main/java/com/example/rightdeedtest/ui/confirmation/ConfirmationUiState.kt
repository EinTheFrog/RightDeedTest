package com.example.rightdeedtest.ui.confirmation

sealed interface ConfirmationUiState {
    val loading: Boolean
    val errorMessage: String?
    val phone: String?

    data class Normal(
        override val loading: Boolean = false,
        override val errorMessage: String? = null,
        override val phone: String? = null,
        val codeTextField: String = "",
        val code: String? = null,
    ): ConfirmationUiState
}