package com.dailyrounds.marrow.assignment.db

import androidx.room.Dao
import androidx.room.Query

@Dao
interface UserDao : BaseDao<UserEntity>{
    @Query("SELECT NOT EXISTS(SELECT 1 FROM users WHERE username = :username)")
    suspend fun isUserNameAvailable(username: String) : Boolean

    @Query("SELECT * FROM users WHERE username = :name and password = :password")
    suspend fun getUser(name: String, password: String) : UserEntity?
}