package com.endcodev.beautifullogin.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.endcodev.beautifullogin.R
import com.endcodev.beautifullogin.presentation.ui.components.LinkedText
import com.endcodev.beautifullogin.presentation.ui.components.LoginMailButton
import com.endcodev.beautifullogin.presentation.ui.components.SocialButton
import com.endcodev.beautifullogin.presentation.ui.components.TopGradient
import com.endcodev.beautifullogin.presentation.ui.theme.BeautifulLoginTheme

@Composable
fun SocialLogin(
    onGoogleClick: () -> Unit,
    onGithubClick: () -> Unit,
    onMailClick: () -> Unit,
) {
    Scaffold(
        topBar = { TopGradient() },
        bottomBar = {        LinkedText()
        },
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
            .padding(innerPadding)
            .padding(start = 50.dp, end = 50.dp)
            .fillMaxSize()
        ,

        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {



        SocialButton(
            onGoogleClick,
            painterResource(id = com.google.firebase.database.ktx.R.drawable.googleg_standard_color_18),
            "Google"
        )
        Spacer(modifier = Modifier.height(8.dp))

        SocialButton(
            {},
            painterResource(id = R.drawable.github),
            "GitHub"
        )
        Spacer(modifier = Modifier.height(8.dp))

        LoginMailButton(onMailClick)
        Spacer(modifier = Modifier.height(60.dp))


    }
}


@Preview
@Composable
fun AuthScreenPreview() {
    BeautifulLoginTheme {
        SocialLogin({}, {}, {})
    }
}