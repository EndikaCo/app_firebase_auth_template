package com.endcodev.beautifullogin.presentation.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.endcodev.beautifullogin.presentation.ui.theme.BeautifulLoginTheme
import com.endcodev.beautifullogin.presentation.ui.theme.Orange3

@Composable
fun IconButton(onButtonClick: () -> Unit, icon: ImageVector, text: String, color: Color?) {
    Button(
        onClick = { onButtonClick() },
        modifier = Modifier.width(200.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = color ?: MaterialTheme.colorScheme.primary
        ),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Icon(
                imageVector = icon,
                contentDescription = text,
                modifier = Modifier.size(20.dp)
            )
            Text(text = text, modifier = Modifier.width(120.dp), textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.width(20.dp))
        }
    }
}

@Preview
@Composable
fun IconButtonPreview() {
    BeautifulLoginTheme {
        IconButton(onButtonClick = {}, Icons.Filled.Save, "SAVE", Orange3)
    }
}

@Preview
@Composable
fun IconButtonPreview2() {
    BeautifulLoginTheme {
        IconButton(onButtonClick = {}, Icons.Filled.Cancel, "CANCEL", null)
    }
}