package com.dailyrounds.marrow.assignment.db

import androidx.room.Query

interface UserDao : BaseDao<UsersEntity>{
    @Query("SELECT NOT EXISTS(SELECT 1 FROM users WHERE username = :username)")
    fun isUserNameAvailable(username: String) : Boolean
}