package com.dailyrounds.marrow.assignment.ui.screens.signup

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.dailyrounds.marrow.assignment.R
import com.dailyrounds.marrow.assignment.Routes
import com.dailyrounds.marrow.assignment.ui.components.CenterCard
import com.dailyrounds.marrow.assignment.ui.components.TextInput
import com.dailyrounds.marrow.assignment.ui.theme.LocalAppTypo
import com.maxkeppeker.sheets.core.models.base.Header
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.info.InfoDialog
import com.maxkeppeler.sheets.info.models.InfoBody
import com.maxkeppeler.sheets.info.models.InfoSelection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(navController: NavHostController) {
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
            header = Header.Default(title = stringResource(id = R.string.success)),
            body = InfoBody.Default(
                bodyText = state.value.submitOverlaySuccessMessage,
            ),
            selection = InfoSelection(
                negativeButton = null,
                onPositiveClick = {
                    navController.navigate(Routes.LOGIN) {
                        launchSingleTop = true
                    }
                },
            ),

            properties = DialogProperties(dismissOnClickOutside = false, dismissOnBackPress = false)
        )
    }
    CenterCard {
        Text(
            text = stringResource(id = R.string.sign_up), style = LocalAppTypo.current.displayMedium)
        TextInput(
            label = stringResource(R.string.username),
            state = state.value.name,
            keyboardOptions =  KeyboardOptions(imeAction = ImeAction.Next)
        ) {
            signUpViewModel.validateUsername(it)
        }
        TextInput(
            label = stringResource(R.string.password),
            state = state.value.password,
            keyboardOptions =  KeyboardOptions(imeAction = ImeAction.Done),
            visualTransformation = PasswordVisualTransformation()
        ) {
            signUpViewModel.validatePassword(it)
        }
        DropDown(label = stringResource(id = R.string.select_country),
            options = countries,
            getSelectedItem = {
                state.value.country.value.country ?: ""
            }, toString = {
                countries[it]?.country ?: ""
            } , selectItem = {
                signUpViewModel.validateCountry(countryCode = it)
            })
        Button(onClick = {signUpViewModel.handleSubmit()}, modifier = Modifier.fillMaxWidth()) {
            Text(text = stringResource(id = R.string.submit))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> DropDown(
    label: String, options: Map<String, T>,
    getSelectedItem: () -> String,
    toString: (String) -> String,
    selectItem: (String) -> Unit
)  {

    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = {
        expanded = !expanded
    }) {
        OutlinedTextField(
            readOnly = true,
            value = getSelectedItem(),
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