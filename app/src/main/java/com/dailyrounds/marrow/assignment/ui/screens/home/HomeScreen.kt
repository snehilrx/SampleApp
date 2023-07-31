package com.dailyrounds.marrow.assignment.ui.screens.home

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.dailyrounds.marrow.assignment.MyApplication
import com.dailyrounds.marrow.assignment.R
import com.dailyrounds.marrow.assignment.Routes
import com.dailyrounds.marrow.assignment.db.UserEntity
import com.dailyrounds.marrow.assignment.ui.components.CenterCard


@Composable
fun HomeScreen(navController: NavHostController, user: UserEntity?) {
    if (user == null) {
        navController.navigate(Routes.LOGIN)
    } else {
        CenterCard {
            Text(text = user.username)
            Text(text = user.countryCode)
            Button(onClick = {
                MyApplication.instance.currentUser = null
                navController.navigate(Routes.LOGIN){
                    popUpTo(Routes.HOME)
                    launchSingleTop = true
                }
            }) {
                Text(text = stringResource(R.string.logout))
            }
        }
    }
}