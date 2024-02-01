package com.example.rightdeedtest.ui.navigation

enum class Destination(val path: String, val arguments: List<String>) {
    LOGIN(path = "login", arguments = emptyList()),
    CONFIRMATION(path = "confirmation", arguments = listOf("phone")),
    RESULT(path = "result", arguments = listOf("phone", "code"));

    fun getArgumentsPath(): String {
        val pathBuilder = StringBuilder(path)
        for (arg in arguments)
            pathBuilder.append("/{$arg}")
        return pathBuilder.toString()
    }

    fun buildPath(argumentValues: List<String>): String {
        val pathBuilder = StringBuilder(path)
        for (arg in argumentValues) {
            pathBuilder.append("/$arg")
        }
        return pathBuilder.toString()
    }
}