package com.example.mainactivity

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(User::class), version = 1)
abstract class UserDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    fun login(username: String, password: String): Any{
        val user = userDao().getByCredentials(username, password);
        val result = Result
        if (user != null) {
            if (user.banned) {
                return "Jūs esate užblokuotas";
            }
            return user
        }

        return "Tokio vartotojo nėra";
    }
}