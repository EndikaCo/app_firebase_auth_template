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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.endcodev.beautifullogin.R
import com.endcodev.beautifullogin.presentation.ui.components.AuthLinks
import com.endcodev.beautifullogin.presentation.ui.components.EmailTextField
import com.endcodev.beautifullogin.presentation.ui.components.LogoText
import com.endcodev.beautifullogin.presentation.ui.components.RectangleButton
import com.endcodev.beautifullogin.presentation.ui.components.TopGradient
import com.endcodev.beautifullogin.presentation.ui.theme.BeautifulLoginTheme
import com.endcodev.beautifullogin.presentation.utils.UiText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Composable
fun ResetPasswordScreen(
    email: String,
    isLoginEnabled: Boolean,
    onAuthClick: () -> Unit,
    onSignUpClick: () -> Unit,
    onLogInClick: () -> Unit,
    onEmailChanged: (String) -> Unit,
    errorChannel: Flow<UiText>,
    isLoading: Boolean,
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
        content = { padding ->
            ResetPasswordContent(
                padding = padding,
                email = email,
                onAuthClick = onAuthClick,
                isLoginEnabled = isLoginEnabled,
                onEmailChanged = onEmailChanged,
                isLoading = isLoading
            )
        },
        bottomBar = {
            AuthLinks(
                link1Text = stringResource(R.string.log_in),
                link2Text = stringResource(R.string.sign_up),
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
    onEmailChanged: (String) -> Unit,
    isLoading: Boolean
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
        Text(
            text = stringResource(R.string.check_spam),
            color = MaterialTheme.colorScheme.surface,
            textAlign = TextAlign.Center,
            fontFamily = FontFamily(
                Font(R.font.chakrapetchbold, FontWeight.Bold)
            )
        )
        Spacer(modifier = Modifier.height(20.dp))
        EmailTextField(email = email, error = null, onTextChanged = onEmailChanged)
        RectangleButton(text = stringResource(R.string.reset), loginEnabled = isLoginEnabled, onAuthClick)
    }

    if (isLoading)
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator()
        }
}

@Preview
@Composable
fun ResetPasswordScreenPreview() {
    BeautifulLoginTheme {

        val a = UiText.DynamicString("error")
        val x: Flow<UiText> = flowOf(a)

        ResetPasswordScreen(
            email = "email",
            isLoginEnabled = true,
            onAuthClick = {},
            onSignUpClick = {},
            onLogInClick = {},
            onEmailChanged = {},
            errorChannel = x,
            isLoading = true,
        )
    }
}