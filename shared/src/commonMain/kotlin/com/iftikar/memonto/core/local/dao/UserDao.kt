package com.iftikar.memonto.core.local.dao

import androidx.room3.Dao
import androidx.room3.Query
import androidx.room3.Upsert
import com.iftikar.memonto.core.local.model.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Upsert
    suspend fun saveUser(userEntity: UserEntity)

    @Query("SELECT * FROM user WHERE id = :id")
    fun getUser(id: Long): Flow<UserEntity?>
}