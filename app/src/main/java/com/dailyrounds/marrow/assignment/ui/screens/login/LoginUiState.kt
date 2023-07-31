package com.dailyrounds.marrow.assignment.ui.screens.login

import androidx.compose.ui.text.input.TextFieldValue
import com.dailyrounds.marrow.assignment.data.InputState

data class LoginUiState(
    val name: InputState<TextFieldValue>,
    val password: InputState<TextFieldValue>,
    val submitOverlaySuccessMessage : String = "",
    val submitOverlayFailureMessage : String = ""
) {
    fun valid(): Boolean {
        return name.value.text.isNotEmpty() && password.value.text.isNotEmpty()
    }

    fun newUiState(
        newUsername: InputState<TextFieldValue> = name,
        newPassword: InputState<TextFieldValue> = password,
        successMessage: String = submitOverlaySuccessMessage,
        failureMessage: String = submitOverlayFailureMessage
    ): LoginUiState {
        return  LoginUiState(newUsername, newPassword, successMessage, failureMessage)
    }
}