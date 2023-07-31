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
    private val application: MyApplication = MyApplication.instance
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
        viewModelScope.launch {
            val errorText = if (!repo.checkIfUsernameIsAvailable(username.text)) {
                application.getString(R.string.username_is_already_taken)
            } else if(username.text.isBlank()) {
                application.getString(R.string.username_cannot_be_blank)
            }else {
                ""
            }
            uiState.value = uiState.value.newUiState(newUsername = InputState(username, errorText))
        }
    }

    fun validatePassword(
        password: TextFieldValue = TextFieldValue()
    ) {

        val errorText = if (!passwordPattern.matches(password.text)) {
            if (password.text.length < 8) {
                application.getString(R.string.password_must_have_at_least_8_characters)
            } else if (!password.text.any { it.isDigit() }) {
                application.getString(R.string.password_must_contain_at_least_one_digit)
            } else if (!password.text.any { it in "!@#\$%&()" }) {
                application.getString(R.string.password_must_contain_at_least_one_special_character)
            } else if (!password.text.any { it.isLowerCase() }) {
                application.getString(R.string.password_must_contain_at_least_one_lowercase_letter)
            } else if (!password.text.any { it.isUpperCase() }) {
                application.getString(R.string.password_must_contain_at_least_one_uppercase_letter)
            } else {
                ""
            }
        } else {
            ""
        }
        uiState.value =
            uiState.value.newUiState(newPassword = InputState(password, errorText))
    }


    fun validateCountry(
        countryCode: String? = ""
    ) {
        //  no rules for country as defaults to india
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

    fun handleSubmit() {
        validateUsername(uiState.value.name.value)
        validatePassword(uiState.value.password.value)
        viewModelScope.launch {
            if (uiState.value.valid()) {
                repo.saveUser(uiState.value)
                uiState.value =
                    uiState.value.newUiState(successMessage = application.getString(R.string.signup_success))
            }
        }
    }


    companion object {
        val passwordPattern = Regex("^(?=.*[0-9])(?=.*[!@#\$%&()])(?=.*[a-z])(?=.*[A-Z]).{8,}$")
        private val BLANK: InputState<TextFieldValue> = InputState(TextFieldValue(""), "")
    }
}