package com.iftikar.memonto.core.domain.repository

import com.iftikar.memonto.core.model.User
import kotlinx.coroutines.flow.Flow

interface UtilRepository {
    suspend fun saveUser(name: String)
    fun getUser(): Flow<User?>
}