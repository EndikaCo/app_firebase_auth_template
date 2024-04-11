package com.endcodev.beautifullogin.presentation.ui.screens

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
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.endcodev.beautifullogin.domain.model.HomeUiState
import com.endcodev.beautifullogin.presentation.ui.theme.BeautifulLoginTheme

@Composable
fun ProfileScreen(
    state: HomeUiState,
    onLogOutClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onSaveButton: () -> Unit,
    goBackClick: () -> Unit,
    onMailChanged: (String) -> Unit,
    onNameChanged: (String) -> Unit,
) {

    var editMode by remember { mutableStateOf(false) }

    Scaffold(
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
                Row(
                    modifier = Modifier
                        .border(1.dp, Color.White, RoundedCornerShape(5.dp))
                        .padding(5.dp)
                        .clickable { editMode = true },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(Icons.Default.Edit, contentDescription = "Save Data", Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(text = "Edit Info")
                }
            }
        },
        bottomBar = {},
        content = { innerPadding ->
            ProfileContent(
                innerPadding,
                state,
                onLogOutClick,
                onDeleteClick,
                editMode,
                onSaveButton,
                {
                    editMode = false
                    //todo
                },
                onMailChanged,
                onNameChanged
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
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier.padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            state.image.let { url ->
                if (url != null) {
                    val painter = rememberImagePainter(data = url)
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

            MyTextView(state.email, onMailChanged, editMode)
            MyTextView(state.userName, onNameChanged, editMode)

            if (editMode) {
                Button(onClick = { onSaveButton() }, modifier = Modifier.width(200.dp)) {
                    Text(text = "Save")
                }
                Button(onClick = { onCancelButton() }, modifier = Modifier.width(200.dp)) {
                    Text(text = "Cancel")
                }
            } else {
                Button(onClick = { onLogOutClick() }, modifier = Modifier.width(200.dp)) {
                    Text(text = "LOG OUT")
                }
                Button(onClick = { onDeleteClick() }, modifier = Modifier.width(200.dp)) {
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
            .width(250.dp)
            .clip(RoundedCornerShape(5.dp))
            .border(1.dp, Color.White, RoundedCornerShape(5.dp))
    else
        Modifier

    BasicTextField(
        value = value,
        onValueChange = { onValueChange(it) },
        readOnly = !editMode,
        cursorBrush = SolidColor(Color.Yellow)

    ) {
        Box(
            modifier = mod,
            contentAlignment = Alignment.Center
        ) {
            Text(text = value, Modifier.padding(5.dp))
        }
    }
    if (editMode)
        Spacer(modifier = Modifier.height(10.dp))

}

@Preview
@Composable
fun ProfileScreenPreview() {
    BeautifulLoginTheme {
        ProfileScreen(HomeUiState(), {}, {}, {}, {}, {}, {})
    }
}