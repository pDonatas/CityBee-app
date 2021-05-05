package com.example.mainactivity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Car(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val manufacturer: String,
    val model: String,
    val manufacturing_year: Int,
    val price: Float
)