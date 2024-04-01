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
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.endcodev.beautifullogin.domain.HomeUiState
import com.endcodev.beautifullogin.presentation.ui.theme.BeautifulLoginTheme

@Composable
fun ProfileScreen(
    state: HomeUiState,
    onLogOutClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Scaffold(
        topBar = {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(Icons.Default.ArrowBackIosNew, contentDescription = "Back Arrow")
                Icon(Icons.Default.Save, contentDescription = "Save Data")
            }
        },
        bottomBar = {},
        content = { innerPadding -> ProfileContent(innerPadding, state, onLogOutClick, onDeleteClick) }
    )
}

@Composable
fun ProfileContent(
    innerPadding: PaddingValues,
    state: HomeUiState,
    onLogOutClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier.padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            state.auth?.currentUser?.photoUrl.let { url ->
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
            BasicTextField(
                value = state.auth?.currentUser?.email ?: "No Email",
                onValueChange = {},
                readOnly = true
            ) {
                Box(
                    modifier = Modifier
                        .width(200.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .border(1.dp, Color.White, RoundedCornerShape(5.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "email@gmail.com", Modifier.padding(5.dp))
                }

            }
            Spacer(modifier = Modifier.height(10.dp))
            BasicTextField(
                value = state.auth?.currentUser?.displayName ?: "No name",
                onValueChange = {},
                readOnly = true
            ) {
                Box(
                    modifier = Modifier
                        .width(200.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .border(1.dp, Color.White, RoundedCornerShape(5.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "name", Modifier.padding(5.dp))
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Button(onClick = {onLogOutClick()}, modifier = Modifier.width(200.dp)) {
                Text(text = "LOG OUT")
            }
            Button(onClick = {onDeleteClick()}, modifier = Modifier.width(200.dp)) {
                Text(text = "Delete Account")
            }
        }
    }
}

@Preview
@Composable
fun ProfileScreenPreview() {
    BeautifulLoginTheme {
        ProfileScreen(HomeUiState(), {},{})
    }
}