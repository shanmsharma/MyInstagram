package com.sharma.authentication.ui.signup

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.sharma.common.Screen
import com.sharma.common.Screen.Login

@Composable
fun SignUpScreen(viewModel: SignUpViewModel = hiltViewModel(), navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val signUpState by viewModel.signUpState.collectAsState()
    val passwordValidationState by viewModel.passwordValidationState.collectAsState()

    Log.d("sharma", "Signup screen")
    LaunchedEffect(signUpState) {
        if (signUpState is SignUpState.Success) {
            Log.d("sharma", "Account created")
            navController.navigate(Login.route) {
                popUpTo(Screen.SignUp.route) { inclusive = true } // Clears SignUp from backstack
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                viewModel.validatePassword(it)
            },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
        )

        // Password strength validation UI
        PasswordValidationUI(passwordValidationState)

        Spacer(modifier = Modifier.height(16.dp))

        when (signUpState) {
            is SignUpState.Loading -> CircularProgressIndicator()
            is SignUpState.Error -> Text(
                text = (signUpState as SignUpState.Error).message,
                color = MaterialTheme.colorScheme.error
            )
            else -> Unit
        }

        Button(
            onClick = {  viewModel.signUp(email, password) },

            modifier = Modifier.fillMaxWidth(),
            enabled = passwordValidationState.hasUppercase && passwordValidationState.hasNumber && passwordValidationState.hasSpecialChar
        ) {
            Text("Create Account")
        }
    }
}

@Composable
fun PasswordValidationUI(state: PasswordValidationState) {
    Column(modifier = Modifier.fillMaxWidth()) {
        PasswordRequirement("One Uppercase Letter", state.hasUppercase)
        PasswordRequirement("One Number", state.hasNumber)
        PasswordRequirement("One Special Character", state.hasSpecialChar)
    }
}

@Composable
fun PasswordRequirement(text: String, isValid: Boolean) {
    val color = when {
        isValid -> Color.Green
        else -> Color.Gray
    }
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = if (isValid) Icons.Default.Check else Icons.Default.Close,
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(text, color = color)
    }
}
