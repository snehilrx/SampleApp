package com.dailyrounds.marrow.assignment.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import com.dailyrounds.marrow.assignment.data.InputState

@Composable
fun TextInput(
    label: String,
    state: InputState<TextFieldValue>,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    validationCallback: (TextFieldValue) -> Unit,
    ) {
    OutlinedTextField(
        value = state.value,
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        isError = state.isValid(),
        onValueChange = {
            validationCallback.invoke(it)
        },
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        supportingText = {
            if (!state.isValid()) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = state.error,
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        trailingIcon = {
            if (!state.isValid())
                Icon(Icons.Filled.Warning, "error", tint = MaterialTheme.colorScheme.error)
        },
        label = { Text(label) },
    )
}