package com.iftikar.memonto.core.local.model

import androidx.room3.Entity
import androidx.room3.PrimaryKey
import com.iftikar.memonto.core.model.User

/**
 * I am saving the user's name in db
 */
@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String
)

fun UserEntity.asExternalModel(): User {
    return User(
        id = id,
        name = name
    )
}
