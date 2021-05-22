package com.example.mainactivity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User (
    @PrimaryKey(autoGenerate = true) val id: Int,
    val username: String,
    val email: String,
    val password: String,
    val money: Double,
    var banned: Boolean = false,
    val role: Int = 0
)