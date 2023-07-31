package com.dailyrounds.marrow.assignment.ui.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.navOptions
import com.dailyrounds.marrow.assignment.R
import com.dailyrounds.marrow.assignment.Routes
import com.dailyrounds.marrow.assignment.ui.components.CenterCard
import com.dailyrounds.marrow.assignment.ui.components.TextInput
import com.dailyrounds.marrow.assignment.ui.theme.LocalAppTypo
import com.dailyrounds.marrow.assignment.ui.theme.SampleAppTheme
import com.maxkeppeker.sheets.core.models.base.Header
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.info.InfoDialog
import com.maxkeppeler.sheets.info.models.InfoBody
import com.maxkeppeler.sheets.info.models.InfoSelection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavHostController, showLoggedOutMessage: MutableState<Boolean>) {
    val loginViewModel: LoginViewModel = viewModel()
    val state = remember {
        loginViewModel.uiState
    }
    if (showLoggedOutMessage.value) {
        InfoDialog(
            state = rememberUseCaseState(
                visible = true,
                onCloseRequest = { }),
            header = Header.Default(title = stringResource(id = R.string.success)),
            body = InfoBody.Default(
                bodyText = stringResource(id = R.string.logged_out_successfully),
            ),
            selection = InfoSelection(
                negativeButton = null,
                onPositiveClick = {
                  showLoggedOutMessage.value = false
                },
            ),
            properties = DialogProperties(dismissOnClickOutside = false, dismissOnBackPress = false)
        )
    }
    if (state.value.submitOverlaySuccessMessage.isNotEmpty()) {
        InfoDialog(
            state = rememberUseCaseState(
                visible = true,
                onCloseRequest = { }),
            header = Header.Default(title = stringResource(id = R.string.success)),
            body = InfoBody.Default(
                bodyText = state.value.submitOverlaySuccessMessage,
            ),
            selection = InfoSelection(
                negativeButton = null,
                onPositiveClick = {
                    navController.navigate(Routes.HOME,) {
                        popUpTo(Routes.LOGIN) {
                            inclusive = true
                        }
                    }
                },
            ),
            properties = DialogProperties(dismissOnClickOutside = false, dismissOnBackPress = false)
        )
    } 
    CenterCard {
        Text(
            text = stringResource(id = R.string.app_name), style = LocalAppTypo.current.displayMedium)
        TextInput(
            label = stringResource(R.string.username),
            state = state.value.name,
            keyboardOptions =  KeyboardOptions(imeAction = ImeAction.Next)) {
            loginViewModel.validateUsername(it)
        }
        TextInput(
            label = stringResource(R.string.password),
            state = state.value.password,
            keyboardOptions =  KeyboardOptions(imeAction = ImeAction.Done),
            visualTransformation = PasswordVisualTransformation()
        ) {
            loginViewModel.validatePassword(it)
        }
        Text(text = state.value.submitOverlayFailureMessage, color = MaterialTheme.colorScheme.error)
        Row(
            horizontalArrangement = Arrangement.spacedBy(SampleAppTheme.dimens.grid_0_5),
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                onClick = {
                    navController.navigate(Routes.SIGNUP, navOptions = navOptions {
                        launchSingleTop = true
                    })
                }) {
                Text(text = stringResource(id = R.string.sign_up))
            }
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                onClick = { loginViewModel.handleSubmit() }
            ) {
                Text(text = stringResource(id = R.string.login))
            }
        }
    }
}
