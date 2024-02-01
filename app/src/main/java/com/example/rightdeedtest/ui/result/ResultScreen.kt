package com.example.rightdeedtest.ui.result

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rightdeedtest.R

@Composable
fun ResultScreen(
    viewModel: ResultViewModel,
    phone: String,
    code: String,
    navigateToConfirmation: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getToken(phone = phone, code = code)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        if (uiState.loading) {
            CircularProgressIndicator()
        } else {
            when (val currentState = uiState) {
                is ResultUiState.Success -> {
                    SuccessResultScreen(uiState = currentState)
                }
                is ResultUiState.Failure -> {
                    FailureResultScreen(
                        navigateToConfirmation = navigateToConfirmation,
                    )
                }
            }
        }
    }
}

@Composable
private fun SuccessResultScreen(
    uiState: ResultUiState.Success,
) {
    if (uiState.phone == null || uiState.token == null) return
    ResultText(text = stringResource(R.string.result_success, uiState.phone, uiState.token))
}

@Composable
private fun FailureResultScreen(
    navigateToConfirmation: () -> Unit
) {
    ResultText(text = stringResource(id = R.string.result_failure))
    Spacer(modifier = Modifier.height(20.dp))
    Button(
        onClick = navigateToConfirmation
    ) {
        Text(text = stringResource(id = R.string.resend_code))
    }
}

@Composable
private fun ResultText(
    modifier: Modifier = Modifier,
    text: String,
) {
    Text(
        modifier = modifier,
        fontSize = 20.sp,
        text = text,
    )
}

@Preview
@Composable
private fun SuccessResultScreenPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        SuccessResultScreen(uiState = ResultUiState.Success())
    }
}

@Preview
@Composable
private fun FailureResultScreenPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        FailureResultScreen(navigateToConfirmation = {})
    }
}