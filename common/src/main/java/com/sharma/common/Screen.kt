package com.sharma.common

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object SignUp : Screen("signup")
}