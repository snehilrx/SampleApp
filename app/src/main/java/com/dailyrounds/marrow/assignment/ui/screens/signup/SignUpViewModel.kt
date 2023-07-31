package com.dailyrounds.marrow.assignment.ui.screens.signup

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dailyrounds.marrow.assignment.MyApplication
import com.dailyrounds.marrow.assignment.R
import com.dailyrounds.marrow.assignment.data.Country
import com.dailyrounds.marrow.assignment.data.InputState
import com.dailyrounds.marrow.assignment.ui.screens.commons.CommonRepository
import kotlinx.coroutines.launch

class SignUpViewModel() : ViewModel() {


    // when using hilt we can use injections
    private val application: MyApplication = MyApplication
    private val repo: CommonRepository = CommonRepository(application)

    val countries = repo.getCountries()

    private val defaultCountry: InputState<Country> = InputState(
        Country("IN", countries["IN"]?.country ?: "", countries["IN"]?.region ?: ""),
        ""
    )

    private val initialState: SignUpUiState = SignUpUiState(BLANK, BLANK, defaultCountry)

    val uiState = mutableStateOf(initialState)

    fun validateUsername(
        username: TextFieldValue = TextFieldValue()
    ) {
        //  checks if user already exists or not in db
        if (repo.checkIfUsernameIsAvailable(username.text)) {
            uiState.value = uiState.value.newUiState(newUsername = InputState(username.text, ""))
        } else {
            uiState.value = uiState.value.newUiState(newUsername = InputState(username.text, application.getString(R.string.username_is_already_taken)))
        }
    }

    fun validatePassword(
        password: TextFieldValue = TextFieldValue()
    ) {
        val errorText = if (isPasswordValid(password.text)) {
            ""
        } else {
            application.getString(R.string.incorrect_password)
        }
        uiState.value = uiState.value.newUiState(newPassword = InputState(password.text, errorText))
    }

    fun validateCountry(
        countryCode: String = ""
    ) {
        //  no rules for country was specified in the assessment document.
        uiState.value = uiState.value.newUiState(
            newCountry = InputState(
                Country(
                    countryCode,
                    countries[countryCode]?.country,
                    countries[countryCode]?.region,
                ), ""
            )
        )
    }

    private fun isPasswordValid(password: String): Boolean {
        return passwordRegex.matcher(password).matches()
    }

    fun handleSubmit() {
        viewModelScope.launch {
            if (uiState.value.valid()) {
                // this will trigger the focus to edit text contain the error
                uiState.value = uiState.value.newUiState()
            } else {
                repo.saveUser(uiState.value)
                uiState.value = uiState.value.newUiState(successMessage = MyApplication.getString(R.string.signup_success))
            }
        }
    }


    companion object {
        private val passwordRegex =
            "^(?=.*[0-9])(?=.*[!@#$%&()])(?=.*[a-z])(?=.*[A-Z]).{8,}$".toPattern()
        private val BLANK: InputState<String> = InputState("", "")
    }
}