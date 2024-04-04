package com.endcodev.beautifullogin.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.endcodev.beautifullogin.R
import com.endcodev.beautifullogin.domain.AuthUiState
import com.endcodev.beautifullogin.presentation.ui.components.AuthLinks
import com.endcodev.beautifullogin.presentation.ui.components.EmailTextField
import com.endcodev.beautifullogin.presentation.ui.components.LogoText
import com.endcodev.beautifullogin.presentation.ui.components.PasswordTextField
import com.endcodev.beautifullogin.presentation.ui.components.RectangleButton
import com.endcodev.beautifullogin.presentation.ui.components.TopGradient
import com.endcodev.beautifullogin.presentation.ui.theme.BeautifulLoginTheme

@Composable
fun MailLoginScreen(
    uiState: AuthUiState,
    onAuthClick: () -> Unit,
    onSignUpClick: () -> Unit,
    onResetClick: () -> Unit,
    onEmailChanged: (String) -> Unit,
    onPassChanged: (String) -> Unit,
    onGoogleClick: () -> Unit
) {
    Scaffold(
        topBar = { TopGradient() },
        content = { pad -> LoginContent(pad, uiState, onAuthClick, onEmailChanged, onPassChanged, onGoogleClick) },
        bottomBar = {
            AuthLinks(
                link1Text = "Reset password",
                link2Text = "Sign Up",
                onLink1Click = onResetClick,
                onLink2Click = onSignUpClick
            )
        },
        containerColor = Color.Transparent
    )
}

@Composable
fun LoginContent(
    pad: PaddingValues,
    uiState: AuthUiState,
    onLoginClick: () -> Unit,
    onEmailChanged: (String) -> Unit,
    onPassChanged: (String) -> Unit,
    onGoogleClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(pad)
            .padding(start = 40.dp, end = 40.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LogoText()
        EmailTextField(
            email = uiState.email,
            error = uiState.emailError,
            onTextChanged = onEmailChanged
        )
        PasswordTextField(
            password = uiState.password,
            placeholder = stringResource(R.string.enter_pass),
            error = null,
            onTextChanged = onPassChanged
        )
        RectangleButton(text = "LOG IN", loginEnabled = uiState.isAuthButtonEnabled, onLoginClick)
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Preview (showBackground = true, backgroundColor = 0xFF2D4A64)
@Composable
fun LoginScreenPreview() {
    BeautifulLoginTheme {
        MailLoginScreen(
            AuthUiState(
                email = "email",
                password = "password",
                isAuthButtonEnabled = true,
            ),
            onAuthClick = {},
            onSignUpClick = {},
            onResetClick = {},
            onEmailChanged = {},
            onPassChanged = {},
            onGoogleClick = {}
        )
    }
}