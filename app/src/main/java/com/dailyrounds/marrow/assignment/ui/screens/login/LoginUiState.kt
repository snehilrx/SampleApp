package com.dailyrounds.marrow.assignment.ui.screens.login

import com.dailyrounds.marrow.assignment.data.InputState

data class LoginUiState(
    val name: InputState<String>,
    val password: InputState<String>,
    val submitOverlaySuccessMessage : String = ""
) {
    fun valid(): Boolean {
        return name.value.isNotEmpty() && password.value.isNotEmpty()
    }

    fun newUiState(
        newUsername: InputState<String> = name,
        newPassword: InputState<String> = password,
        successMessage: String = submitOverlaySuccessMessage
    ): LoginUiState {
        return  LoginUiState(newUsername, newPassword, successMessage)
    }
}