package com.endcodev.beautifullogin.presentation.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
@Composable
fun LinkedText() {
    val annotatedString = buildAnnotatedString {
        withStyle(style = SpanStyle(fontSize = 15.sp)) { // Adjust the font size here
            append("By joining, you agree to the ")

            pushStringAnnotation(tag = "policy", annotation = "https://google.com/policy")
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append("privacy policy")
            }
            pop()

            append(" and ")

            pushStringAnnotation(tag = "terms", annotation = "https://google.com/terms")

            withStyle(style = SpanStyle(/*color = MaterialTheme.colorScheme.primary,*/ fontWeight = FontWeight.Bold)) {
                append("terms of use.")
            }

            pop()
        }
    }

    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        ClickableText(
            text = annotatedString,
            onClick = { offset ->
                annotatedString.getStringAnnotations(tag = "policy", start = offset, end = offset)
                    .firstOrNull()?.let {
                    }

                annotatedString.getStringAnnotations(tag = "terms", start = offset, end = offset)
                    .firstOrNull()?.let {
                    }
            },
            modifier = Modifier.padding(start = 16.dp, end = 16.dp)
        )
    }
}