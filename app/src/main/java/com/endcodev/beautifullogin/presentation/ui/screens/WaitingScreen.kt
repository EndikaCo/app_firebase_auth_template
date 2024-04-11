package com.endcodev.beautifullogin.presentation.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.endcodev.beautifullogin.domain.model.AuthUiState
import kotlinx.coroutines.delay

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun WaitingScreen(uiState: AuthUiState) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        var text by remember { mutableStateOf("Sending mail to\n${uiState.email}") }
        var sending by remember { mutableStateOf(true) }

        LaunchedEffect(key1 = text) {
            delay(3000) // delay for 3 seconds
            text = "Waiting your verification..."
            sending = false
        }

        Text(
            text = text,
            color = MaterialTheme.colorScheme.surfaceVariant,
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
        )
        Spacer(modifier = Modifier.height(16.dp))
        if (sending)
            MovingIcon()
        else
            CircularProgressIndicator(color = MaterialTheme.colorScheme.surfaceVariant)
    }
}

@Composable
fun MovingIcon() {
    val infiniteTransition = rememberInfiniteTransition(label = "mailIcon")

    val initialMovement by infiniteTransition.animateFloat(
        initialValue = -100f,
        targetValue = 100f,
        animationSpec = infiniteRepeatable(
            animation = tween(3500, easing = LinearEasing),
        ), label = "initialMovement"
    )

    Icon(
        imageVector = Icons.Default.Email,
        contentDescription = null,
        tint = MaterialTheme.colorScheme.surfaceVariant,
        modifier = Modifier.offset(x = initialMovement.dp)
    )
}