package com.dailyrounds.marrow.assignment.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "users"
)
data class UsersEntity(
    @PrimaryKey
    val username: String,
    val password: String,
    val countryCode: String,
)
