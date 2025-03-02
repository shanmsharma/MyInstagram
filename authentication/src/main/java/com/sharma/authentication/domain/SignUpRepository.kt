package com.sharma.authentication.domain


interface SignUpRepository {
    suspend fun signUp(email: String, password: String): Result<Boolean>
}