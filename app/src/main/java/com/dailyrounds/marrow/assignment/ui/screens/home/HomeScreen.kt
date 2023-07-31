package com.dailyrounds.marrow.assignment.ui.screens.home

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.dailyrounds.marrow.assignment.R
import com.dailyrounds.marrow.assignment.Routes
import com.dailyrounds.marrow.assignment.db.UserEntity


@Composable
fun HomeScreen(navController: NavHostController, user: UserEntity?) {
    if (user == null) {
        navController.navigate(Routes.LOGIN)
    }
    Button(onClick = {

    }) {
        Text(text = stringResource(R.string.logout))
    }
}