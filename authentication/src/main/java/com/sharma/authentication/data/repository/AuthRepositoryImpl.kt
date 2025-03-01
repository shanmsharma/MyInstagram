package com.sharma.authentication.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.sharma.authentication.domain.AuthRepository
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl(private val firebaseAuth:FirebaseAuth) : AuthRepository {
    override suspend fun login(email: String, password: String): Result<Boolean> {
        return try {
            firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}