package com.endcodev.beautifullogin.presentation.ui.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LinkedText(onCheck: (Boolean) -> Unit, checkedState: Boolean) {
    val annotatedString = buildAnnotatedString {
        withStyle(style = SpanStyle(fontSize = 15.sp)) { // Adjust the font size here
            append("By checking, you agree to the ")

            pushStringAnnotation(tag = "policy", annotation = "https://google.com/policy")
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append("privacy policy")
            }
            pop()

            append(" and ")

            pushStringAnnotation(tag = "terms", annotation = "https://google.com/terms")

            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append("terms of use.")
            }

            pop()
        }
    }

    var open by remember {
        mutableStateOf(false)
    }

    if (open) {
        OpenFirebasePolicy()
        open = false
    }

    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(
            checked = checkedState,
            onCheckedChange = { onCheck(it) },
            colors = CheckboxDefaults.colors(
                checkmarkColor = Color.Black,
                uncheckedColor = Color.Black
            )
        )

        ClickableText(
            text = annotatedString,
            onClick = { offset ->
                annotatedString.getStringAnnotations(tag = "policy", start = offset, end = offset)
                    .firstOrNull()?.let {
                        open = true
                    }

                annotatedString.getStringAnnotations(tag = "terms", start = offset, end = offset)
                    .firstOrNull()?.let {
                        open = true
                    }
            },
            modifier = Modifier.padding(start = 8.dp, end = 16.dp)
        )

    }
}


@Composable
fun OpenFirebasePolicy() {
    val context = LocalContext.current
    val openURL = Intent(Intent.ACTION_VIEW)
    openURL.data = Uri.parse("https://firebase.google.com/terms")
    context.startActivity(openURL)
}