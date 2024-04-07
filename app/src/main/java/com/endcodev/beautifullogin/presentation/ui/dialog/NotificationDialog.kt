package com.endcodev.beautifullogin.presentation.ui.dialog


import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun NotificationDialog(title: String, message: String, onAccept: () -> Unit) {
    var showDialog by remember { mutableStateOf(true) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                showDialog = false
                onAccept()
            },
            title = { Text(text = title) },
            text = { Text(message) },
            confirmButton = {
                Button(onClick = {
                    showDialog = false
                    onAccept()
                }) {
                    Text("CONTINUE")
                }
            }
        )
    }
}

@Composable
@Preview
fun NotificationDialogPreview() {
    NotificationDialog("Title", "Message", {})
}