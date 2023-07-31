package com.dailyrounds.marrow.assignment.ui.screens.login

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dailyrounds.marrow.assignment.MyApplication
import com.dailyrounds.marrow.assignment.R
import com.dailyrounds.marrow.assignment.data.InputState
import com.dailyrounds.marrow.assignment.ui.screens.commons.CommonRepository
import kotlinx.coroutines.launch
import javax.security.auth.login.LoginException

class LoginViewModel(): ViewModel() {

    // when using hilt we can use injections
    private val application: MyApplication = MyApplication.instance
    private val repo: CommonRepository = CommonRepository(application)

    private val initialState: LoginUiState = LoginUiState(BLANK, BLANK)

    val uiState = mutableStateOf(initialState)

    fun validateUsername(
        username: TextFieldValue = TextFieldValue()
    ) {
        //  checks if user already exists or not in db
        if (username.text.isNotEmpty()) {
            uiState.value = uiState.value.newUiState(newUsername = InputState(username, ""))
        } else {
            uiState.value = uiState.value.newUiState(newUsername = InputState(username, application.getString(R.string.enter_username)))
        }
    }

    fun validatePassword(
        password: TextFieldValue = TextFieldValue()
    ) {
        val errorText = if (password.text.isNotEmpty()) {
            ""
        } else {
            application.getString(R.string.incorrect_password)
        }
        uiState.value = uiState.value.newUiState(newPassword = InputState(password, errorText))
    }

    fun handleSubmit() {
        viewModelScope.launch {
            validatePassword(uiState.value.password.value)
            validateUsername(uiState.value.name.value)
            if (uiState.value.valid()) {
                try {
                    application.currentUser = repo.login(uiState.value)
                    uiState.value = uiState.value.newUiState(successMessage = application.getString(R.string.login_success), failureMessage = "")
                } catch (e: LoginException) {
                    uiState.value = uiState.value.newUiState(failureMessage =  application.getString(R.string.incorrect_password), successMessage = "")
                }
            }
        }
    }


    companion object {
        private val BLANK: InputState<TextFieldValue> = InputState(TextFieldValue(""), "")
    }
}