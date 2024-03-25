package com.endcodev.beautifullogin.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.endcodev.beautifullogin.presentation.ui.theme.BeautifulLoginTheme
import com.google.firebase.database.ktx.R

@Composable
fun GoogleLoginButton() {
    Row(
        modifier = Modifier
            .imePadding()
            .size(60.dp)
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(MaterialTheme.colorScheme.surfaceVariant, Color.Transparent),
                    radius = Float.POSITIVE_INFINITY
                ),
            )
            .padding(start = 8.dp, end = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Image(
            painter = painterResource(id = R.drawable.googleg_standard_color_18),
            contentDescription = "",
            modifier = Modifier.size(36.dp)
        )
    }
}

@Composable
@Preview
fun GoogleLoginButtonPreview() {
    BeautifulLoginTheme {
        GoogleLoginButton()
    }
}
