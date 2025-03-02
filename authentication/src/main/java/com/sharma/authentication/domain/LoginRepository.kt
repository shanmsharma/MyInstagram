package com.sharma.authentication.domain

interface LoginRepository {
    suspend fun login(email: String, password: String): Result<Boolean>
}