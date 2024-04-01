package com.endcodev.beautifullogin.presentation.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.endcodev.beautifullogin.presentation.ui.theme.BeautifulLoginTheme

@Composable
fun OnEditTextField(value: String?, onValueChange: (String) -> Unit) {
    BasicTextField(
        value = value ?: "null",
        onValueChange = { onValueChange(it) },
        readOnly = false
    ) {
        Row(
            modifier = Modifier
                .width(200.dp)
                .clip(RoundedCornerShape(5.dp))
                .border(1.dp, Color.White, RoundedCornerShape(5.dp)),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(text = value ?: "null", Modifier.padding(5.dp))
            Icon(
                Icons.Default.Edit,
                contentDescription = "Edit Icon",
                modifier = Modifier
                    .size(24.dp)
                    .padding(4.dp)
            )
        }
    }
}

@Composable
fun ReadOnlyTextField(value: String?, onValueChange: (String) -> Unit) {
    BasicTextField(
        value = value ?: "null",
        onValueChange = { onValueChange(it) },
        readOnly = true
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Text(text = "name", Modifier.padding(5.dp))
        }
    }
}

@Preview
@Composable
fun OnEditTextFieldPreview() {
    BeautifulLoginTheme {
        OnEditTextField(value = "name", onValueChange = {})
    }
}

@Preview
@Composable
fun ReadOnlyTextFieldPreview() {
    BeautifulLoginTheme {
        ReadOnlyTextField(value = "name", onValueChange = {})
    }
}
