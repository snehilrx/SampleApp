package com.dailyrounds.marrow.assignment.ui.screens.commons

import com.dailyrounds.marrow.assignment.MyApplication
import com.dailyrounds.marrow.assignment.data.Countries
import com.dailyrounds.marrow.assignment.data.CountryRegion
import com.dailyrounds.marrow.assignment.db.UserEntity
import com.dailyrounds.marrow.assignment.ui.screens.login.LoginUiState
import com.dailyrounds.marrow.assignment.ui.screens.signup.SignUpUiState
import com.google.gson.Gson
import javax.security.auth.login.LoginException

class CommonRepository(val application: MyApplication) {

    fun getCountries(): Map<String, CountryRegion> {
        val rawText = application.assets.open(COUNTRIES_FILE_NAME).bufferedReader().readText()
        return gson.fromJson(rawText, Countries::class.java).data.countryRegionMap
    }

    suspend fun saveUser(value: SignUpUiState) {
        application.db.userDao().insert(
            UserEntity(
                username = value.name.value,
                password = value.password.value,
                countryCode = value.country.value.countryCode ?: "",
            )
        )
    }

    fun checkIfUsernameIsAvailable(username: String): Boolean {
        return application.db.userDao().isUserNameAvailable(username)
    }

    @Throws(LoginException::class)
    fun login(value: LoginUiState): UserEntity {
        val user = application.db.userDao().getUser(value.name.value, value.password.value)
            ?: throw LoginException("No user is found")
        return user
    }


    companion object {
        private val gson = Gson()
        private const val COUNTRIES_FILE_NAME: String = "countries.json"
    }

}
