package com.example.mainactivity

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mainactivity.data.Result
import java.io.IOException

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

    fun logout() {
        // TODO: revoke authentication
    }
}