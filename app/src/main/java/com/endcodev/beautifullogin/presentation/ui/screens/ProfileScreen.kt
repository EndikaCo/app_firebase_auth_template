package com.endcodev.beautifullogin.presentation.ui.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.endcodev.beautifullogin.R
import com.endcodev.beautifullogin.domain.model.HomeUiState
import com.endcodev.beautifullogin.presentation.ui.components.IconButton
import com.endcodev.beautifullogin.presentation.ui.theme.BeautifulLoginTheme
import com.endcodev.beautifullogin.presentation.ui.theme.Orange2
import com.endcodev.beautifullogin.presentation.utils.UiText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Composable
fun ProfileScreen(
    state: HomeUiState,
    onLogOutClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onSaveButton: () -> Unit,
    goBackClick: () -> Unit,
    onEditModeClick: () -> Unit,
    onMailChanged: (String) -> Unit,
    onNameChanged: (String) -> Unit,
    errorChannel: Flow<UiText>,
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val context : Context = LocalContext.current

    LaunchedEffect(errorChannel) {
        errorChannel.collect { error ->
            snackBarHostState.showSnackbar(
                message = error.asString(context),
                actionLabel = context.getString(R.string.ok),
                withDismissAction = false,
                duration = SnackbarDuration.Indefinite)
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) },
        topBar = {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew,
                    modifier = Modifier.clickable { goBackClick() },
                    contentDescription = "Back Arrow"
                )
            }
        },
        bottomBar = {},
        content = { innerPadding ->
            ProfileContent(
                innerPadding,
                state,
                onLogOutClick,
                onDeleteClick,
                state.editMode,
                onSaveButton = {
                    onEditModeClick()
                    onSaveButton()
                },
                onCancelButton = {
                    onEditModeClick()
                },
                onMailChanged,
                onNameChanged,
                onEditModeClick,
            )
        }
    )
}

@Composable
fun ProfileContent(
    innerPadding: PaddingValues,
    state: HomeUiState,
    onLogOutClick: () -> Unit,
    onDeleteClick: () -> Unit,
    editMode: Boolean,
    onSaveButton: () -> Unit,
    onCancelButton: () -> Unit,
    onMailChanged: (String) -> Unit,
    onNameChanged: (String) -> Unit,
    onEditModeClick: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier.padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            state.image.let { url ->
                if (url != null) {
                    val painter = rememberAsyncImagePainter(model = url)
                    Box(
                        modifier = Modifier
                            .padding(10.dp) //padding to the end
                            .clip(CircleShape)
                            .size(100.dp)
                            .background(MaterialTheme.colorScheme.onSurface)
                            .padding(2.dp)
                            .clickable { }
                    ) {
                        Image(
                            painter = painter,
                            contentDescription = "User profile picture",
                            modifier = Modifier.size(100.dp) // Increase the size as needed
                        )
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .padding(10.dp) //padding to the end
                            .clip(CircleShape)
                            .size(100.dp)
                            .background(MaterialTheme.colorScheme.onSurface)
                            .padding(2.dp)
                            .clickable { },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = "Login Icon",
                            modifier = Modifier.size(100.dp), // Increase the size as needed
                            tint = MaterialTheme.colorScheme.surface
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            MyTextView(state.email, onMailChanged, editMode)
            Spacer(modifier = Modifier.height(10.dp))
            MyTextView(state.userName, onNameChanged, editMode)
            Spacer(modifier = Modifier.height(10.dp))
            MyTextView(state.phone, {  }, editMode)
            Spacer(modifier = Modifier.height(10.dp))

            if (editMode) {
                IconButton(
                    onButtonClick = { onCancelButton() },
                    icon = Icons.Default.Cancel,
                    text = "CANCEL",
                    null
                )
                IconButton(
                    onButtonClick = { onSaveButton() },
                    icon = Icons.Default.Save,
                    text = "SAVE",
                    Orange2
                )

            } else {
                Button(onClick = { onEditModeClick() }, modifier = Modifier.width(200.dp)) {
                    Text(text = "Edit")
                }
                Button(onClick = { onLogOutClick() }, modifier = Modifier.width(200.dp)) {
                    Text(text = "Log out")
                }
                Button(
                    onClick = { onDeleteClick() },
                    modifier = Modifier.width(200.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Orange2)
                ) {
                    Text(text = "Delete Account")
                }
            }

        }
    }
}

@Composable
fun MyTextView(
    value: String,
    onValueChange: (String) -> Unit,
    editMode: Boolean
) {
    val mod: Modifier = if (editMode)
        Modifier
            .clip(RoundedCornerShape(5.dp))
            .border(1.dp, Color.White, RoundedCornerShape(5.dp))
            .padding(5.dp)
    else
        Modifier

    BasicTextField(
        value = value,
        onValueChange = { onValueChange(it) },
        readOnly = !editMode,
        cursorBrush = SolidColor(Color.Yellow),
        textStyle = TextStyle(
            color = Color.White,
            fontSize = 20.sp,
            textAlign = TextAlign.Center
        ),
        decorationBox = { innerTextField ->
            Box(
                modifier = mod,
                contentAlignment = Alignment.Center
            ) {
                innerTextField.invoke()
            }
        }
    )
}

@Preview
@Composable
fun ProfileScreenPreview() {
    BeautifulLoginTheme {

        val a = UiText.DynamicString("error")
        val x: Flow<UiText> = flowOf(a)

        ProfileScreen(
            state = HomeUiState(editMode = false),
            onLogOutClick = {},
            onDeleteClick = {},
            onSaveButton = {},
            goBackClick = {},
            onEditModeClick = {},
            onMailChanged = {},
            onNameChanged = {},
            errorChannel = x,
        )
    }
}

@Preview
@Composable
fun ProfileScreenPreview2() {
    BeautifulLoginTheme {

        val a = UiText.DynamicString("error")
        val x: Flow<UiText> = flowOf(a)

        ProfileScreen(
            state = HomeUiState(editMode = true),
            onLogOutClick = {},
            onDeleteClick = {},
            onSaveButton = {},
            goBackClick = {},
            onEditModeClick = {},
            onMailChanged = {},
            onNameChanged = {},
            errorChannel = x,
        )
    }
}