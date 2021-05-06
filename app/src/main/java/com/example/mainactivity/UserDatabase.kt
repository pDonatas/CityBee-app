package com.example.mainactivity

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mainactivity.Car

@Database(entities = arrayOf(User::class), version = 1)
abstract class UserDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
}