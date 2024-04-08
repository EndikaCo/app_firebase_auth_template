package com.endcodev.beautifullogin.presentation.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.endcodev.beautifullogin.R
import com.endcodev.beautifullogin.presentation.ui.theme.BeautifulLoginTheme


@Composable
fun SocialButton(onClick: () -> Unit, icon: Painter, type: String) {
    Button(
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(
            containerColor =
            MaterialTheme.colorScheme.surfaceVariant,

            ),
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                shape = ShapeDefaults.ExtraLarge
            )            .alpha(0.9f),


        ) {
        Icon(
            painter = icon,
            contentDescription = "$type social login",
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.width(20.dp))
        Text(
            text = "Continue with $type",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontFamily = FontFamily(Font(R.font.chakrapetch))
        )
    }
}


@Preview
@Composable
fun SocialButtonPreview() {
    BeautifulLoginTheme {
        SocialButton(
            {}, painterResource(id = R.drawable.github),
            "GitHub"
        )
    }
}