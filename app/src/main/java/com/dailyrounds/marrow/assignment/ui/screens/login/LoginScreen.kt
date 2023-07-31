package com.dailyrounds.marrow.assignment.ui.screens.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dailyrounds.marrow.assignment.R
import com.dailyrounds.marrow.assignment.data.InputState
import com.maxkeppeker.sheets.core.models.base.Header
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.info.InfoDialog
import com.maxkeppeler.sheets.info.models.InfoBody
import com.maxkeppeler.sheets.info.models.InfoSelection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen() {
    val loginViewModel : LoginViewModel = viewModel()
    val state = remember {
        loginViewModel.uiState
    }
    if  (state.value.submitOverlaySuccessMessage.isNotEmpty()) {
        InfoDialog(
            state = rememberUseCaseState(
                visible = true,
                onCloseRequest = {  }),
            header = Header.Default(title = stringResource(id = R.string.error)),
            body = InfoBody.Default(
                bodyText = state.value.submitOverlaySuccessMessage,
            ),
            selection = InfoSelection(
                onPositiveClick = {
                    // todo handle navigation
                },
            ),
            properties = DialogProperties(dismissOnClickOutside = false, dismissOnBackPress = false)
        )
    }
    Column {
        TextInput(state.value.name) {
            loginViewModel.validateUsername(it)
        }
        TextInput(state.value.password) {
            loginViewModel.validatePassword(it)
        }

        Button(onClick = {loginViewModel.handleSubmit()}) {
            Text(text = stringResource(id = R.string.login))
        }
    }
}

@Composable
private fun TextInput(
    state: InputState<String>,
    validationCallback: (TextFieldValue) -> Unit
) {
    OutlinedTextField(
        value = TextFieldValue(state.value),
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        isError = state.isValid(),
        onValueChange = {
            validationCallback.invoke(it)
        },
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
        label = { Text(stringResource(R.string.username)) },
    )
}
