package com.sharma.authentication.ui.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sharma.authentication.domain.usecase.LoginUseCase
import com.sharma.authentication.domain.usecase.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {

    private val _signUpState = MutableStateFlow<SignUpState>(SignUpState.Idle)
    val signUpState: StateFlow<SignUpState> = _signUpState

    private val _passwordValidationState = MutableStateFlow(PasswordValidationState())
    val passwordValidationState: StateFlow<PasswordValidationState> = _passwordValidationState

    fun signUp(email: String, password: String) {
        if (!validatePassword(password)) {
            _signUpState.value = SignUpState.Error("Weak Password")
            return
        }

        viewModelScope.launch {
            _signUpState.value = SignUpState.Loading
            val result = signUpUseCase(email, password)
            _signUpState.value = if (result.isSuccess) SignUpState.Success else SignUpState.Error("Sign Up Failed")
        }
    }

    fun validatePassword(password: String): Boolean {
        val hasUppercase = password.any { it.isUpperCase() }
        val hasNumber = password.any { it.isDigit() }
        val hasSpecialChar = password.any { "!@#$%^&*()_+-=[]{}|;:',.<>?/`~".contains(it) }

        _passwordValidationState.value = PasswordValidationState(
            hasUppercase = hasUppercase,
            hasNumber = hasNumber,
            hasSpecialChar = hasSpecialChar
        )

        return hasUppercase && hasNumber && hasSpecialChar
    }
}

data class PasswordValidationState(
    val hasUppercase: Boolean = false,
    val hasNumber: Boolean = false,
    val hasSpecialChar: Boolean = false
)


sealed class SignUpState {
    object Idle : SignUpState()
    object Loading : SignUpState()
    object Success : SignUpState()
    data class Error(val message: String) : SignUpState()
}