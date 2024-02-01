package com.example.rightdeedtest.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavArgument
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.rightdeedtest.ui.confirmation.ConfirmationScreen
import com.example.rightdeedtest.ui.confirmation.ConfirmationUiState
import com.example.rightdeedtest.ui.confirmation.ConfirmationViewModel
import com.example.rightdeedtest.ui.login.LoginScreen
import com.example.rightdeedtest.ui.login.LoginUiState
import com.example.rightdeedtest.ui.login.LoginViewModel
import com.example.rightdeedtest.ui.result.ResultScreen
import com.example.rightdeedtest.ui.result.ResultViewModel

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Destination.LOGIN.path
    ) {
        composable(route = Destination.LOGIN.path) {
            val viewModel = hiltViewModel<LoginViewModel>()
            LoginScreen(
                viewModel = viewModel,
                navigateToConfirmation = {
                    val normalUiState = viewModel.uiState.value
                    if (normalUiState !is LoginUiState.Normal) return@LoginScreen
                    navController.navigate(
                        Destination.CONFIRMATION.buildPath(
                            listOf(normalUiState.phoneTextField)
                        )
                    )
                }
            )
        }
        composable(
            route = Destination.CONFIRMATION.getArgumentsPath(),
            arguments = Destination.CONFIRMATION.arguments.map { argName ->
                navArgument(argName) { type = NavType.StringType }
            }
        ) { backStackEntry ->
            val viewModel = hiltViewModel<ConfirmationViewModel>()
            val phoneArgName = Destination.CONFIRMATION.arguments[0]
            val phone = backStackEntry.arguments?.getString(phoneArgName) ?: return@composable
            ConfirmationScreen(
                viewModel = viewModel,
                phone = phone,
                navigateToResult = {
                    val normalUiState = viewModel.uiState.value
                    if (normalUiState !is ConfirmationUiState.Normal) return@ConfirmationScreen
                    navController.navigate(
                        Destination.RESULT.buildPath(
                            listOf(
                                phone,
                                normalUiState.codeTextField
                            )
                        )
                    )
                }
            )
        }

        composable(
            route = Destination.RESULT.getArgumentsPath(),
            arguments = Destination.RESULT.arguments.map { argName ->
                navArgument(argName) { type = NavType.StringType }
            }
        ) { backStackEntry ->
            val viewModel = hiltViewModel<ResultViewModel>()
            val phoneArgName = Destination.RESULT.arguments[0]
            val phone = backStackEntry.arguments?.getString(phoneArgName) ?: return@composable
            val codeArgName = Destination.RESULT.arguments[1]
            val code = backStackEntry.arguments?.getString(codeArgName) ?: return@composable
            ResultScreen(
                viewModel = viewModel,
                phone = phone,
                code = code,
                navigateToConfirmation = {
                    navController.popBackStack()
                }
            )
        }
    }
}