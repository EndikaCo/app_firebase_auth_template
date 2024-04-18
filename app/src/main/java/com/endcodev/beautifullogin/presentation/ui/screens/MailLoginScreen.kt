package com.endcodev.beautifullogin.presentation.ui.screens

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.endcodev.beautifullogin.R
import com.endcodev.beautifullogin.domain.model.AuthUiState
import com.endcodev.beautifullogin.presentation.ui.components.AuthLinks
import com.endcodev.beautifullogin.presentation.ui.components.EmailTextField
import com.endcodev.beautifullogin.presentation.ui.components.LogoText
import com.endcodev.beautifullogin.presentation.ui.components.PasswordTextField
import com.endcodev.beautifullogin.presentation.ui.components.RectangleButton
import com.endcodev.beautifullogin.presentation.ui.components.SocialButton
import com.endcodev.beautifullogin.presentation.ui.components.TopGradient
import com.endcodev.beautifullogin.presentation.ui.theme.BeautifulLoginTheme
import com.endcodev.beautifullogin.presentation.utils.UiText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Composable
fun MailLoginScreen(
    uiState: AuthUiState,
    onAuthClick: () -> Unit,
    onSignUpClick: () -> Unit,
    onResetClick: () -> Unit,
    onEmailChanged: (String) -> Unit,
    onPassChanged: (String) -> Unit,
    onGoogleClick: () -> Unit,
    errorChannel: Flow<UiText>
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val context: Context = LocalContext.current

    LaunchedEffect(errorChannel) {
        errorChannel.collect { error ->
            snackBarHostState.showSnackbar(
                message = error.asString(context),
                withDismissAction = true,
                duration = SnackbarDuration.Short
            )
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) },
        topBar = { TopGradient() },
        content = { pad ->
            LoginContent(
                pad,
                uiState,
                onAuthClick,
                onEmailChanged,
                onPassChanged,
                onGoogleClick
            )
        },
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
        Spacer(modifier = Modifier.height(20.dp))
        SocialButton(onClick = { onGoogleClick() })
    }

    if (uiState.isLoading)
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator()
        }
}

@Preview(showBackground = true, backgroundColor = 0xFF2D4A64)
@Composable
fun LoginScreenPreview() {
    BeautifulLoginTheme {
        val a = UiText.DynamicString("error")
        val x: Flow<UiText> = flowOf(a)

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
            onGoogleClick = {},
            errorChannel = x
        )
    }
}