package com.dailyrounds.marrow.assignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dailyrounds.marrow.assignment.ui.screens.home.HomeScreen
import com.dailyrounds.marrow.assignment.ui.screens.login.LoginScreen
import com.dailyrounds.marrow.assignment.ui.screens.signup.SignUpScreen
import com.dailyrounds.marrow.assignment.ui.theme.SampleAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SampleAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    /// I have not implemented persistent session. User will be signed out when app is closed.
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Routes.LOGIN
    ) {
        composable(Routes.SIGNUP) {
            SignUpScreen(navController)
        }
        composable(Routes.LOGIN,
            arguments = listOf(navArgument("logout") { type = NavType.BoolType; defaultValue = false })
        ) {
            val showLoggedOutMessage = it.arguments?.getBoolean("logout") ?: false
            LoginScreen(navController, remember {
                mutableStateOf(showLoggedOutMessage)
            })
        }
        composable(Routes.HOME) {
            HomeScreen(navController, user = MyApplication.instance.currentUser)
        }
    }
}