package com.dailyrounds.marrow.assignment

import android.app.Application
import androidx.room.Room
import com.dailyrounds.marrow.assignment.db.MyDb
import com.dailyrounds.marrow.assignment.db.UserEntity

object MyApplication : Application() {
    val db = Room.databaseBuilder(this, MyDb::class.java, "sample_db").build()

    var currentUser: UserEntity? = null
}