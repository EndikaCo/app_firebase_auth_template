package com.endcodev.beautifullogin.presentation.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.endcodev.beautifullogin.R

@Composable
fun UserNameTextField(email: String, error: String?, onTextChanged: (String) -> Unit) {
    TextField(
        value = email,
        onValueChange = { onTextChanged(it) },
        modifier = Modifier
            .fillMaxWidth()
            .alpha(0.9F),
        placeholder = {
            Text(
                stringResource(R.string.enter_username),
                modifier = Modifier
                    .fillMaxWidth()
                    .alpha(0.9f)

                    .padding(end = 31.dp), textAlign = TextAlign.Center
            )
        },
        maxLines = 1,
        shape = CircleShape,
        leadingIcon = {
            Icon(
                Icons.Default.Person,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSecondaryContainer
            )
        },
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        supportingText = {
            if (error != null)
                Text(text = error, color = Color.Red, modifier = Modifier.padding(bottom = 8.dp))
        },
    )
}

@Preview
@Composable
fun UserNameTextFieldTextFieldPreview() {
    UserNameTextField(
        email = stringResource(R.string.enter_username),
        error = null,
        onTextChanged = {})
}