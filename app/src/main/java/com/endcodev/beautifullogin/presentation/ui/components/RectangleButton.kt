package com.endcodev.beautifullogin.presentation.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.endcodev.beautifullogin.R
import com.endcodev.beautifullogin.presentation.ui.theme.BeautifulLoginTheme

@Composable
fun RectangleButton(text: String, loginEnabled: Boolean, onLoginClick: () -> Unit) {

    val backgroundColor = MaterialTheme.colorScheme.primary
    val transparentBackgroundColor = backgroundColor.copy(alpha = 0.9f)
    Button(
        onClick = { onLoginClick() },
        enabled = loginEnabled,
        modifier = Modifier
            .fillMaxWidth()
            .border(2.dp, MaterialTheme.colorScheme.surfaceVariant, shape = CircleShape)
            .height(50.dp)
            .focusable(),
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            disabledContainerColor = Color.Transparent,
            containerColor = transparentBackgroundColor
        )
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.surfaceVariant,
            fontFamily = FontFamily(Font(R.font.chakrapetchbold))
        )
    }
}

@Preview
@Composable
fun RectangleButtonPreview() {
    BeautifulLoginTheme {
        RectangleButton(text = "LOG IN", loginEnabled = true, onLoginClick = {})
    }
}