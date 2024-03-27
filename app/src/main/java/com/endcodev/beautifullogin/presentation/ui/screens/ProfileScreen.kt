package com.endcodev.beautifullogin.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.endcodev.beautifullogin.domain.HomeUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(state: HomeUiState, onClick: () -> Unit) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                actions = { },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                )
            )
        },
        bottomBar = {},
        content = { innerPadding -> ProfileContent(innerPadding, state) }
    )
}

@Composable
fun ProfileContent(innerPadding: PaddingValues, state: HomeUiState) {
    Column(modifier = Modifier.padding(innerPadding) ) {
        Text(text = "Profile")
    }
}
