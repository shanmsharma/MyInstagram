package com.sharma.navigation

import androidx.compose.runtime.Composable

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sharma.authentication.ui.login.LoginScreen
import com.sharma.authentication.ui.signup.SignUpScreen
import com.sharma.common.Screen.Login
import com.sharma.common.Screen.SignUp

@Composable
fun AppNavigation(navController : NavHostController) {
   NavHost(navController = navController, startDestination = Login.route) {
        composable(Login.route) { LoginScreen(navController = navController) }
       composable(SignUp.route) { SignUpScreen(navController = navController) }
    }
}
