package com.sharma.user.domain.usecase

import android.net.Uri
import com.sharma.user.domain.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SaveUserProfileUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(username: String, bio: String, profilePicUri: Uri?): Boolean {
        return withContext(Dispatchers.IO) {
            return@withContext userRepository.saveUserProfile(username, bio, profilePicUri)
        }
    }
}