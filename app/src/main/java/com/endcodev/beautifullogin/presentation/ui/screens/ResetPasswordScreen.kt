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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.endcodev.beautifullogin.presentation.ui.components.AuthLinks
import com.endcodev.beautifullogin.presentation.ui.components.EmailTextField
import com.endcodev.beautifullogin.presentation.ui.components.LogoText
import com.endcodev.beautifullogin.presentation.ui.components.RectangleButton
import com.endcodev.beautifullogin.presentation.ui.components.TopGradient
import com.endcodev.beautifullogin.presentation.ui.theme.BeautifulLoginTheme


@Composable
fun ResetPasswordScreen(
    email: String,
    isLoginEnabled: Boolean,
    onAuthClick: () -> Unit,
    onSignUpClick: () -> Unit,
    onLogInClick: () -> Unit,
    onEmailChanged: (String) -> Unit,
) {
    Scaffold(
        topBar = { TopGradient() },
        content = { padding ->
            ResetPasswordContent(
                padding = padding,
                email = email,
                onAuthClick = onAuthClick,
                isLoginEnabled = isLoginEnabled,
                onEmailChanged = onEmailChanged,
            )
        },
        bottomBar = {
            AuthLinks(
                link1Text = "Log In",
                link2Text = "Sign Up",
                onLink1Click = { onLogInClick() },
                onLink2Click = { onSignUpClick() })
        },
        containerColor = Color.Transparent
    )
}

@Composable
fun ResetPasswordContent(
    padding: PaddingValues,
    email: String,
    onAuthClick: () -> Unit,
    isLoginEnabled: Boolean,
    onEmailChanged: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(padding)
            .padding(start = 40.dp, end = 40.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LogoText()
        EmailTextField(email = email, error = null, onTextChanged = onEmailChanged)
        RectangleButton(text = "RESET PASSWORD", loginEnabled = isLoginEnabled, onAuthClick)
    }
}

@Preview
@Composable
fun ResetPasswordScreenPreview() {
    BeautifulLoginTheme {
        ResetPasswordScreen(
            email = "email",
            isLoginEnabled = true,
            onAuthClick = {},
            onLogInClick = {},
            onEmailChanged = {},
            onSignUpClick = {},
        )
    }
}