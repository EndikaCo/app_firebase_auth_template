package com.endcodev.beautifullogin.presentation.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.endcodev.beautifullogin.presentation.ui.theme.BeautifulLoginTheme

@Composable
fun MyEditableTextView(
    value: String,
    onValueChange: (String) -> Unit,
    editMode: Boolean,
    onEditModeClick : () -> Unit
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
            Row(
                modifier = mod,
            ) {
                if (!editMode)
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .align(Alignment.CenterVertically)
                    )
                innerTextField.invoke()
                if (!editMode)
                    Icon(
                        Icons.Default.Edit,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier
                            .size(20.dp)
                            .align(Alignment.CenterVertically)
                            .clickable { onEditModeClick() }
                    )
            }
        }
    )
}

@Preview
@Composable
fun MyEditableTextViewPreview() {
    BeautifulLoginTheme {
        MyEditableTextView(
            value = "Hello",
            onValueChange = {},
            editMode = true,
            onEditModeClick = {}
        )
    }
}

@Preview
@Composable
fun MyEditableTextViewPreview2() {
    BeautifulLoginTheme {
        MyEditableTextView(
            value = "Hello",
            onValueChange = {},
            editMode = false,
            onEditModeClick = {}
        )
    }
}
