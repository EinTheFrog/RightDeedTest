package com.example.rightdeedtest.ui.confirmation

import com.example.rightdeedtest.ui.login.LoginUiState
import com.example.rightdeedtest.ui.login.LoginViewModel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.rightdeedtest.R


@Composable
fun ConfirmationScreen(
    viewModel: ConfirmationViewModel,
    phone: String,
    navigateToResult: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.login(phone)
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        if (uiState.loading) {
            CircularProgressIndicator()
        } else {
            when (val currentState = uiState) {
                is ConfirmationUiState.Normal -> {
                    NormalConfirmationScreen(
                        uiState = currentState,
                        onCodeValueChange = viewModel::updateCodeTextField,
                        navigateToResult = navigateToResult,
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NormalConfirmationScreen(
    uiState: ConfirmationUiState.Normal,
    onCodeValueChange: (String) -> Unit,
    navigateToResult: () -> Unit,
) {
    val codeText = uiState.codeTextField

    Text(text = stringResource(id = R.string.input_code))
    Spacer(modifier = Modifier.height(20.dp))
    OutlinedTextField(
        value = codeText,
        onValueChange = onCodeValueChange,
    )
    Spacer(modifier = Modifier.height(20.dp))
    Button(
        onClick = navigateToResult
    ) {
        Text(text = stringResource(R.string.continue_button))
    }
}

@Preview
@Composable
fun NormalConfirmationScreenPreview() {
    NormalConfirmationScreen(
        uiState = ConfirmationUiState.Normal(),
        onCodeValueChange = {},
        navigateToResult = {},
    )
}