package com.sharma.authentication.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.sharma.authentication.domain.LoginRepository
import com.sharma.authentication.domain.SignUpRepository
import kotlinx.coroutines.tasks.await


class SignUpRepositoryImpl(private val firebaseAuth: FirebaseAuth) : SignUpRepository {
    override suspend fun signUp(email: String, password: String): Result<Boolean> {
        return try {
            firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}