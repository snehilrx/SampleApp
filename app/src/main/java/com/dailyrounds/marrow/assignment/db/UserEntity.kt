package com.dailyrounds.marrow.assignment.db

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(
    tableName = "users"
)
@Parcelize
data class UserEntity(
    @PrimaryKey
    val username: String,
    @ColumnInfo(index = true)
    val password: String,
    val countryCode: String,
) : Parcelable
