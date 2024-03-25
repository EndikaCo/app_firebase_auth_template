package com.endcodev.beautifullogin.presentation.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun LogoText() {
    Text(
        text = "SensorLink",
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.8f),
        fontSize = 48.sp,
        fontWeight = FontWeight.Bold,
    )
}