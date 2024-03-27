package com.endcodev.beautifullogin.presentation.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.endcodev.beautifullogin.R

@Composable
fun LogoText() {
    val context = LocalContext.current
    Text(
        text = context.resources.getString(R.string.app_name),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.8f),
        fontSize = 40.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing= 1.sp,
        lineHeight = 1.sp,
        textAlign = TextAlign.Center,
    )
}