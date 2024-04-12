package com.endcodev.beautifullogin.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.endcodev.beautifullogin.R

@Composable
fun LogoText() {
    Column {
        Image(
            painter = painterResource(id = R.drawable.firebase),
            contentDescription = "",
            modifier = Modifier.size(120.dp)
        )
        Spacer(modifier = Modifier.height(25.dp))
    }
}