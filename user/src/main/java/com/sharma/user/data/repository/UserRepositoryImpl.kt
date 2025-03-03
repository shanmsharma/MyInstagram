package com.sharma.user.data.repository

import android.net.Uri
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.sharma.user.domain.UserRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage
): UserRepository {
    override suspend fun saveUserProfile(username: String, bio: String, profilePicUri: Uri?): Boolean {
        val userId = auth.currentUser?.uid ?: return false

        val userDocRef = firestore.collection("users").document(userId)

        // ✅ Step 1: Upload profile picture to Firebase Storage
        var profilePicUrl: String? = null
        profilePicUri?.let { uri ->
            val imageRef = storage.reference.child("profile_pics/$userId.jpg")
            imageRef.putFile(uri).await()  // Upload Image
            profilePicUrl = imageRef.downloadUrl.await().toString()  // Get Image URL
        }

        // ✅ Step 2: Save user profile in Firestore
        val userData = mapOf(
            "userId" to userId,
            "username" to username,
            "email" to (auth.currentUser?.email ?: ""),
            "profilePicUrl" to profilePicUrl,
            "bio" to bio,
            "createdAt" to com.google.firebase.Timestamp.now()
        )

        return try {
            userDocRef.set(userData).await() // Save user data in Firestore
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun getUserProfile(): Map<String, Any>? {
        Log.d("sharma", "getUserProfile")
        val userId = auth.currentUser?.uid ?: return null
        Log.d("sharma", userId)
        val snapshot = firestore.collection("users").document(userId).get().await()
        Log.d("sharma","${snapshot.data}")
        return snapshot.data
    }
}
