package com.example.mainactivity

import androidx.room.*

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM User")
    suspend fun getAll(): List<User>

    @Query("SELECT * FROM User WHERE username LIKE :username LIMIT 1")
    suspend fun getByName(username: String): List<User>

    @Query("SELECT * FROM User WHERE username=:username AND password=:password")
    fun getByCredentials(username: String, password: String): User?

    @Delete
    suspend fun deleteUser(user: User)

    @Update
    fun updateUser(user: User)

    @Query("UPDATE User SET banned=1 WHERE id=:id")
    fun banUser(id: Int)

    @Query("SELECT EXISTS(SELECT * FROM User WHERE username = :username)")
    fun userExists(username: String) : Boolean


}