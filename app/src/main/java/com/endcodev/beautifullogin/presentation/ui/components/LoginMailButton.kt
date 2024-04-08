package com.endcodev.beautifullogin.presentation.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.endcodev.beautifullogin.R
import com.endcodev.beautifullogin.presentation.ui.theme.BeautifulLoginTheme


@Composable
fun LoginMailButton(onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(
            contentColor =
            MaterialTheme.colorScheme.surfaceVariant
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = ShapeDefaults.ExtraLarge
            ).alpha(0.9f),
    ) {
        Icon(
            imageVector = Icons.Default.MailOutline,
            contentDescription = "Login with Mail",
            tint = MaterialTheme.colorScheme.onSecondary
        )
        Spacer(modifier = Modifier.width(20.dp))
        Text(
            text = "Continue with Mail",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSecondary,
            fontFamily = FontFamily(
                Font(R.font.chakrapetchbold, FontWeight.Bold)
            )
        )
    }
}

@Preview
@Composable
fun LoginMailButtonExample(){
    BeautifulLoginTheme {
        LoginMailButton({})
    }
}