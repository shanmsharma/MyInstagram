package com.sharma.user.domain.usecase

import com.sharma.user.domain.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetUserProfileUseCase(private val userRepository:UserRepository) {
    suspend operator fun invoke(): Map<String, Any>? {
        return withContext(Dispatchers.IO) {
            userRepository.getUserProfile()
        }
    }
}