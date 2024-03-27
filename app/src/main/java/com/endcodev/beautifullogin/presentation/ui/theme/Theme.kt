package com.endcodev.beautifullogin.presentation.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView

private val DarkColorScheme = darkColorScheme(
    primary = Yellow,
    secondary = PurpleGrey80,
    tertiary = Pink80,
    secondaryContainer = Color(0xFF252525),
    surfaceVariant = Color(0xFF1D1C1C),
    )


@Composable
fun BeautifulLoginTheme(
    content: @Composable () -> Unit
) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = DarkColorScheme.surfaceVariant.toArgb()
            window.navigationBarColor = DarkColorScheme.surfaceVariant.toArgb()
        }
    }


    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}