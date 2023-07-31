package com.dailyrounds.marrow.assignment.ui.screens.signup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
fun SignUpScreen() {
    val signUpViewModel : SignUpViewModel = viewModel()
    val state = remember {
        signUpViewModel.uiState
    }
    val countries = remember {
        signUpViewModel.countries
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
            signUpViewModel.validateUsername(it)
        }
        TextInput(state.value.password) {
            signUpViewModel.validatePassword(it)
        }
        DropDown(label = stringResource(id = R.string.country),
            options = countries,
            getItem = {
                countries["IN"]?.country ?: ""
            }, toString = {
                countries[it]?.country ?: ""
            } , selectItem = {
                signUpViewModel.validateCountry(countryCode = it)
            })
        Button(onClick = {signUpViewModel.handleSubmit()}) {
            Text(text = stringResource(id = R.string.submit))
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> DropDown(
    label: String, options: Map<String, T>,
    getItem: () -> String,
    toString: (String) -> String,
    selectItem: (String) -> Unit
)  {

    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = {
        expanded = !expanded
    }) {
        OutlinedTextField(
            readOnly = true,
            value = toString(getItem()),
            onValueChange = { },
            label = { Text(label, Modifier.fillMaxWidth()) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            singleLine = true,
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = Modifier.menuAnchor()
        )
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = {
            expanded = false
        }) {
            options.forEach { selectionOption ->
                DropdownMenuItem(onClick = {
                    selectItem(selectionOption.key)
                    expanded = false
                }, text = {
                    Text(text = toString(selectionOption.key))
                })
            }
        }
    }
}