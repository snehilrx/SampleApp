package com.dailyrounds.marrow.assignment.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [UserEntity::class],
    version = 1,
    exportSchema = false,
)
abstract class MyDb : RoomDatabase() {
    abstract fun userDao() : UserDao
}