package com.dailyrounds.marrow.assignment.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.dailyrounds.marrow.assignment.MyApplication
import com.dailyrounds.marrow.assignment.R
import com.dailyrounds.marrow.assignment.Routes
import com.dailyrounds.marrow.assignment.db.UserEntity
import com.dailyrounds.marrow.assignment.ui.components.CenterCard


@Composable
fun HomeScreen(navController: NavHostController, user: UserEntity?) {
    CenterCard {
        Row(horizontalArrangement = Arrangement.SpaceEvenly) {
            Text(text = stringResource(id = R.string.username))
            Text(text = user?.username ?: "")
        }
        Row(horizontalArrangement = Arrangement.SpaceEvenly) {
            Text(text = stringResource(id = R.string.country_code))
            Text(text = user?.countryCode ?: "")
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                MyApplication.instance.currentUser = null
                navController.navigate(Routes.LOGIN_LOGOUT) {
                    popUpTo(Routes.HOME) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }
            }) {
            Text(text = stringResource(R.string.logout))
        }
    }
}