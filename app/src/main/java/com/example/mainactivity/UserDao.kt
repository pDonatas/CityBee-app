package com.example.mainactivity

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM User")
    suspend fun getAll(): List<User>

    @Query("SELECT * FROM User WHERE username LIKE :username LIMIT 1")
    suspend fun getByName(username: String): List<User>

    @Delete
    suspend fun deleteUser(user: User)
}