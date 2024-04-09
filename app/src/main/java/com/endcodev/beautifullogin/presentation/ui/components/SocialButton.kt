package com.endcodev.beautifullogin.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun SocialButton(onClick: () -> Unit) {
    Box(modifier = Modifier.clickable { onClick() })
    {
        Image(
            painter = painterResource(id = com.google.android.gms.base.R.drawable.googleg_disabled_color_18),
            contentDescription = "",
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.surface),
            modifier = Modifier
                .size(28.dp)
                .alpha(0.9f),
        )
    }
}