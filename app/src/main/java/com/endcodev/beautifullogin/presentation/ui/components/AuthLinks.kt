package com.endcodev.beautifullogin.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AuthLinks(
    link1Text: String,
    link2Text: String,
    onLink1Click: () -> Unit,
    onLink2Click: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp, start = 40.dp, end = 40.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = link1Text,
            fontSize = 17.sp,
            modifier = Modifier
                .clickable { onLink1Click() },
            color = MaterialTheme.colorScheme.surfaceVariant
        )
        Text(
            text = link2Text,
            fontSize = 17.sp,
            modifier = Modifier
                .clickable { onLink2Click() },
            color = MaterialTheme.colorScheme.surfaceVariant
        )
    }
}