package com.dailyrounds.marrow.assignment.db

import androidx.room.Query

interface UserDao : BaseDao<UserEntity>{
    @Query("SELECT NOT EXISTS(SELECT 1 FROM users WHERE username = :username)")
    fun isUserNameAvailable(username: String) : Boolean

    @Query("SELECT * FROM users WHERE username = :name and password = :password")
    fun getUser(name: String, password: String) : UserEntity?
}