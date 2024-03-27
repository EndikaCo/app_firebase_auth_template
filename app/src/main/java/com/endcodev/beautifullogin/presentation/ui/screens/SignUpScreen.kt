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
import com.endcodev.beautifullogin.presentation.ui.components.EmailTextField
import com.endcodev.beautifullogin.presentation.ui.components.LoginLink
import com.endcodev.beautifullogin.presentation.ui.components.LogoText
import com.endcodev.beautifullogin.presentation.ui.components.PasswordTextField
import com.endcodev.beautifullogin.presentation.ui.components.RectangleButton
import com.endcodev.beautifullogin.presentation.ui.components.TopGradient
import com.endcodev.beautifullogin.presentation.ui.components.UserNameTextField
import com.endcodev.beautifullogin.presentation.ui.theme.BeautifulLoginTheme


@Composable
fun SignUpScreen(
    uiState: AuthUiState,
    onAuthButtonClick: () -> Unit,
    onLogInClick: () -> Unit,
    onEmailChanged: (String) -> Unit,
    onPassChanged: (String) -> Unit,
    onUserNameChanged: (String) -> Unit,
) {
    Scaffold(
        topBar = { TopGradient() },
        content = { pad ->
            SignUpContent(
                pad,
                uiState,
                onAuthButtonClick,
                onEmailChanged,
                onPassChanged,
                onUserNameChanged
            )
        },
        bottomBar = { LoginLink(onLogInClick) },
        containerColor = Color.Transparent
    )
}

@Composable
fun SignUpContent(
    pad: PaddingValues,
    uiState: AuthUiState,
    onAuthButtonClick: () -> Unit,
    onEmailChanged: (String) -> Unit,
    onPassChanged: (String) -> Unit,
    onUserNameChanged: (String) -> Unit
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
        UserNameTextField(
            email = uiState.userName,
            error = uiState.userNameError,
            onUserNameChanged
        )
        PasswordTextField(
            password = uiState.password,
            placeholder = stringResource(R.string.enter_pass),
            error = uiState.passwordError,
            onTextChanged = onPassChanged
        )
        RectangleButton(
            text = "SIGN UP",
            loginEnabled = uiState.isAuthButtonEnabled,
            onAuthButtonClick
        )
    }
}

@Preview
@Composable
fun SignUpScreenPreview() {
    BeautifulLoginTheme {
        SignUpScreen(
            AuthUiState(
                email = "Email",
                password = "Password",
                isAuthButtonEnabled = true,
            ),
            onAuthButtonClick = {},
            onLogInClick = {},
            onEmailChanged = {},
            onPassChanged = {},
            onUserNameChanged = {}
        )
    }
}