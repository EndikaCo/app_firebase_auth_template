package com.endcodev.beautifullogin.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoginLink(
    onLink1Click: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Log In",
            fontSize = 18.sp,
            modifier = Modifier
                .clickable { onLink1Click() },
            color = MaterialTheme.colorScheme.surfaceVariant
        )
    }
}

@Preview
@Composable
fun LoginLinkPreview() {
    LoginLink(
        onLink1Click = {}
    )
}