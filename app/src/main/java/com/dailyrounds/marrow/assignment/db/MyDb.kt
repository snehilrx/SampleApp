package com.dailyrounds.marrow.assignment.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [UsersEntity::class],
    version = 1,
    exportSchema = true,
)
abstract class MyDb : RoomDatabase() {
    abstract fun userDao() : UserDao
}