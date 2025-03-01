package com.sharma.authentication.domain.usecase

import com.sharma.authentication.domain.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String): Result<Boolean> {
        return withContext(Dispatchers.IO) {
            authRepository.login(email, password)
        }
    }
}