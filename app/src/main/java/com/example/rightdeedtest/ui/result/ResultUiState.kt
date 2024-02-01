package com.example.rightdeedtest.ui.result

import com.example.rightdeedtest.ui.login.LoginUiState

sealed interface ResultUiState {
    val loading: Boolean
    val errorMessage: String?
    val phone: String?

    data class Success(
        override val loading: Boolean = false,
        override val errorMessage: String? = null,
        override val phone: String? = null,
        val token: String? = null,
    ): ResultUiState

    data class Failure(
        override val loading: Boolean = false,
        override val errorMessage: String? = null,
        override val phone: String? = null,
    ): ResultUiState
}