package com.example.mainactivity

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mainactivity.CarViewModel
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class CarViewModelFactory(val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CarViewModel::class.java)) {
            return CarViewModel(context) as T
        }
        throw IllegalArgumentException()
    }
}