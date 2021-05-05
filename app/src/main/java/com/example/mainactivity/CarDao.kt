package com.example.mainactivity

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.mainactivity.Car

@Dao
interface CarDao {
    @Insert
    suspend fun insertCar(car: Car)

    @Query("SELECT * FROM Car")
    suspend fun getAll(): List<Car>

    @Query("SELECT * FROM Car WHERE manufacturing_year>:year")
    suspend fun getNewer(year: Int): List<Car>

    @Query("SELECT * FROM Car WHERE price < :price")
    suspend fun getCheaper(price: Float): List<Car>

    @Delete
    suspend fun deleteCar(car: Car)
}