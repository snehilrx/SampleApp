package com.dailyrounds.marrow.assignment.ui.screens.signup

import androidx.compose.ui.text.input.TextFieldValue
import com.dailyrounds.marrow.assignment.data.Country
import com.dailyrounds.marrow.assignment.data.InputState

data class SignUpUiState(
    val name: InputState<TextFieldValue>,
    val password: InputState<TextFieldValue>,
    val country: InputState<Country>,
    val submitOverlaySuccessMessage : String = ""
) {
    fun valid(): Boolean {
        return name.isValid() && password.isValid() && country.isValid()
    }

    fun newUiState(
        newUsername: InputState<TextFieldValue> = name,
        newPassword: InputState<TextFieldValue> = password,
        newCountry: InputState<Country> = country,
        successMessage: String = submitOverlaySuccessMessage
    ): SignUpUiState {
        return  SignUpUiState(newUsername, newPassword, newCountry, successMessage)
    }
}