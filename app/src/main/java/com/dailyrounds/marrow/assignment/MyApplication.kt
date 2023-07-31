package com.dailyrounds.marrow.assignment

import android.app.Application
import androidx.room.Room
import com.dailyrounds.marrow.assignment.db.MyDb
import com.dailyrounds.marrow.assignment.db.UserEntity
import java.util.concurrent.TimeUnit

class MyApplication : Application() {
    lateinit var db: MyDb

    override fun onCreate() {
        super.onCreate()
        instance = this
        db = Room.databaseBuilder(this, MyDb::class.java, "sample_db")
            .setAutoCloseTimeout(1, TimeUnit.HOURS).build()
    }

    var currentUser: UserEntity? = null

    companion object {
        lateinit var instance: MyApplication
            private set
    }
}