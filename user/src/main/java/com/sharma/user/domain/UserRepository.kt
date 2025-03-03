package com.sharma.user.domain

import android.net.Uri

interface UserRepository {
    suspend fun saveUserProfile(username: String, bio: String, profilePicUri: Uri?): Boolean
    suspend fun getUserProfile(): Map<String, Any>?
}