package com.endcodev.beautifullogin.presentation.ui.screens

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.endcodev.beautifullogin.domain.HomeUiState
import com.endcodev.beautifullogin.presentation.ui.theme.BeautifulLoginTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(state: HomeUiState, onProfileClick: () -> Unit) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                actions = { TopBarActions(onProfileClick, state) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                )
            )
        },
        bottomBar = {},
        content = { innerPadding -> HomeContent(innerPadding) }
    )
}

@Composable
fun HomeContent(innerPadding: PaddingValues,) {
    Box(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceVariant),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Home Screen")
    }
}

@Composable
fun TopBarActions(onClick: () -> Unit, state: HomeUiState) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
        ) {
        Text(
            text = "${state.auth?.currentUser?.email}\n${state.auth?.currentUser?.displayName}",
            textAlign = TextAlign.End
        )
        Spacer(modifier = Modifier.width(10.dp)) // Espacio entre el texto y el ícono
        Box(
            modifier = Modifier
                .padding(end = 10.dp) //padding to the end
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.onSurface)
                .padding(2.dp)
                .clickable { onClick() }
        ) {
            OpenAccount(state.auth?.currentUser?.photoUrl)
        }
    }
}

@Composable
fun OpenAccount(photoUrl: Uri?) {
    Box(
        modifier = Modifier
            .size(40.dp),
        contentAlignment = Alignment.Center
    ) {
        photoUrl?.let {
            Image(
                painter = rememberImagePainter(data = it),
                contentDescription = "User Photo",
                modifier = Modifier.size(40.dp)
            )
        } ?: Icon(
            Icons.Default.Person,
            contentDescription = "Login Icon",
            modifier = Modifier.size(40.dp),
            tint = MaterialTheme.colorScheme.surfaceVariant
        )
    }
}

@Preview
@Composable
fun HomePreview() {
    BeautifulLoginTheme {
        HomeScreen(HomeUiState(), {})
    }
}