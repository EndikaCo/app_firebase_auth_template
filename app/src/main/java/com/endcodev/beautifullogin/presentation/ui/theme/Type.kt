package com.endcodev.beautifullogin.presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.endcodev.beautifullogin.R

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily(
            Font(R.font.chakrapetch),
            Font(R.font.chakrapetchbold, FontWeight.Bold)
        ),
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)