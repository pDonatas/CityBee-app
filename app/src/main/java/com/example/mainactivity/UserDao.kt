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



    @Query("UPDATE User SET banned=0 WHERE id=:id")
    fun unbanUser(id: Int)

    @Query("UPDATE User SET username=:username, email=:email, money=:money WHERE id=:id")
    fun editUser(id: Int, username: String?, email: String?, money: String?)

    @Query("UPDATE User SET password=:password WHERE id=:id")
    fun changeUserPassword(id: Int, password: String)
}