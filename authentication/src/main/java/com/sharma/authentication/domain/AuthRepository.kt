package com.sharma.authentication.domain

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<Boolean>
}