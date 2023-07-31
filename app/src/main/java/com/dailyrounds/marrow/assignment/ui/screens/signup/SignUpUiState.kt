package com.dailyrounds.marrow.assignment.ui.screens.signup

import com.dailyrounds.marrow.assignment.data.Country
import com.dailyrounds.marrow.assignment.data.InputState

data class SignUpUiState(
    val name: InputState<String>,
    val password: InputState<String>,
    val country: InputState<Country>,
    val submitOverlaySuccessMessage : String = ""
) {
    fun valid(): Boolean {
        return name.isValid() && password.isValid() && country.isValid()
    }

    fun newUiState(
        newUsername: InputState<String> = name,
        newPassword: InputState<String> = password,
        newCountry: InputState<Country> = country,
        successMessage: String = submitOverlaySuccessMessage
    ): SignUpUiState {
        return  SignUpUiState(newUsername, newPassword, newCountry, successMessage)
    }
}