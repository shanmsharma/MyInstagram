package com.sharma.authentication.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.sharma.authentication.domain.LoginRepository
import kotlinx.coroutines.tasks.await

class LoginRepositoryImpl(private val firebaseAuth:FirebaseAuth) : LoginRepository {
    override suspend fun login(email: String, password: String): Result<Boolean> {
        return try {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}