package com.example.rightdeedtest.ui.login

import android.widget.EditText
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.rightdeedtest.R
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment


@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    navigateToConfirmation: () -> Unit,
) {
        val uiState by viewModel.uiState.collectAsState()

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
                is LoginUiState.Normal -> {
                    NormalLoginScreen(
                        uiState = currentState,
                        onPhoneValueChange = viewModel::updatePhoneTextField,
                        navigateToConfirmation = navigateToConfirmation,
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NormalLoginScreen(
    uiState: LoginUiState.Normal,
    onPhoneValueChange: (String) -> Unit,
    navigateToConfirmation: () -> Unit,

) {
    val phoneText = uiState.phoneTextField

    Text(text = stringResource(id = R.string.input_phone))
    Spacer(modifier = Modifier.height(20.dp))
    OutlinedTextField(
        value = phoneText,
        onValueChange = onPhoneValueChange,
    )
    Spacer(modifier = Modifier.height(20.dp))
    Button(
        onClick = navigateToConfirmation
    ) {
        Text(text = stringResource(R.string.continue_button))
    }
}

@Preview
@Composable
fun NormalLoginScreenPreview() {
    NormalLoginScreen(
        uiState = LoginUiState.Normal(),
        onPhoneValueChange = {},
        navigateToConfirmation = {},
    )
}