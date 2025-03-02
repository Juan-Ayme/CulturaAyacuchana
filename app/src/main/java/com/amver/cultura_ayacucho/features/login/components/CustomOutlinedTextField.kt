package com.amver.cultura_ayacucho.features.login.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun CustomOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholderText: String,
    leadingIconPainter: Painter,
    leadingIconDesciption: String,
    modifier: Modifier = Modifier,
    unfocuseBorderColorInput: Color = Color.Transparent,
    focusBorderColor: Color = Color.White.copy(alpha = 0.2f),
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(text = placeholderText) },
        leadingIcon = {
            Icon(
                painter = leadingIconPainter,
                contentDescription = leadingIconDesciption,
                tint = Color.White.copy(alpha = 0.7f)
            )
        },
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = unfocuseBorderColorInput,
            focusedBorderColor = focusBorderColor
        )
    )
}