package com.sharma.authentication.domain.usecase

import com.sharma.authentication.domain.SignUpRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class SignUpUseCase(private val signUpRepository: SignUpRepository) {
    suspend operator fun invoke(email: String, password: String): Result<Boolean> {
        return withContext(Dispatchers.IO) {
            signUpRepository.signUp(email, password)
        }
    }
}