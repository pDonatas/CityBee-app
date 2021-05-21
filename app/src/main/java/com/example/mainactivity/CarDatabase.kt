package com.example.mainactivity

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mainactivity.Car

@Database(entities = arrayOf(Car::class), version = 1)
abstract class CarDatabase : RoomDatabase() {

    abstract fun carDao(): CarDao
}