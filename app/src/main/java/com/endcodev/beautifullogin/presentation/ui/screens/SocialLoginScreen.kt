package com.endcodev.beautifullogin.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.endcodev.beautifullogin.R
import com.endcodev.beautifullogin.domain.AuthUiState
import com.endcodev.beautifullogin.presentation.ui.theme.BeautifulLoginTheme

@Composable
fun SocialLogin(
    state: AuthUiState,
    onGoogleClick: () -> Unit,
    onGithubClick: () -> Unit,
    onMailClick: () -> Unit,
) {
    Scaffold(
        topBar = {},
        bottomBar = {},
        content = { innerPadding ->
            AuthContent(
                innerPadding,
                onGoogleClick,
                onGithubClick,
                onMailClick
            )
        },
        containerColor = Color.Transparent
    )
}

@Composable
fun AuthContent(
    innerPadding: PaddingValues,
    onGoogleClick: () -> Unit,
    onGithubClick: () -> Unit,
    onMailClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(text = "Welcome to Beautiful Login", fontSize = 20.sp)
        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = { onGoogleClick() }) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.width(200.dp)
            ) {
                Icon(
                    painter = painterResource(id = com.google.firebase.database.collection.R.drawable.googleg_standard_color_18),
                    contentDescription = "User Account"
                )
                Spacer(modifier = Modifier.width(20.dp))
                Text(text = "Continue with Google")
            }
        }
        Button(onClick = { onGithubClick() }) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.width(200.dp)

            ) {
                Icon(
                    painter = painterResource(id = R.drawable.github),
                    contentDescription = "User Account",
                )
                Spacer(modifier = Modifier.width(20.dp))
                Text(text = "Continue with Github")
            }
        }
        Button(onClick = { onMailClick() }) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.width(200.dp)

            ) {
                Icon(Icons.Default.MailOutline, contentDescription = "User Account")
                Spacer(modifier = Modifier.width(20.dp))

                Text(text = "Continue with Mail")
            }
        }
    }
}

@Preview
@Composable
fun AuthScreenPreview() {
    BeautifulLoginTheme {
        SocialLogin(AuthUiState(), {}, {}, {})
    }
}