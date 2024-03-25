package com.endcodev.beautifullogin.presentation.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.endcodev.beautifullogin.R

@Composable
fun PasswordTextField(
    password: String,
    placeholder: String,
    error: String?,
    onTextChanged: (String) -> Unit
) {

    var passwordVisibility by remember {
        mutableStateOf(false)
    }

    TextField(
        value = password,
        onValueChange = { onTextChanged(it) },
        modifier = Modifier
            .fillMaxWidth()
            .alpha(0.9f),
        placeholder = {
            Text(
                placeholder,
                modifier = Modifier
                    .fillMaxWidth(), textAlign = TextAlign.Center
            )
        },
        maxLines = 1,
        leadingIcon = {
            if (passwordVisibility)
                Icon(
                    Icons.Default.LockOpen,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSecondaryContainer
                )
            else
                Icon(
                    Icons.Default.Lock,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSecondaryContainer
                )
        },
        shape = CircleShape,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                if (passwordVisibility)
                    Icon(
                        painterResource(id = R.drawable.visibility_24),
                        contentDescription = stringResource(R.string.password_visibility_off)
                    )
                else
                    Icon(
                        painterResource(id = R.drawable.visibility_off_24),
                        contentDescription = stringResource(R.string.password_visibility_on)
                    )
            }
        },

        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        visualTransformation = if (passwordVisibility)
            VisualTransformation.None
        else
            PasswordVisualTransformation(),
        supportingText = {
            if (error != null)
                Text(text = error, color = Color.Red, modifier = Modifier.padding(bottom = 8.dp))
        },
    )
}

@Preview
@Composable
fun PasswordTextFieldPreview() {
    PasswordTextField(password = "password", error = null, placeholder = "Enter Password") {}
}
