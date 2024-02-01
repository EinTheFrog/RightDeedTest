package com.example.rightdeedtest.model.domain

import androidx.compose.ui.text.toLowerCase
import java.lang.IllegalArgumentException

enum class UserStatus {
    NEW, OLD;

    companion object {
        fun fromString(str: String): UserStatus {
            return when(str.lowercase()) {
                "new" -> NEW
                "old" -> OLD
                else -> throw IllegalArgumentException("Cannot convert this string to UserStatus")
            }
        }
    }
}