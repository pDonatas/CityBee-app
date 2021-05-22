package com.example.mainactivity

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.mainactivity.CarDatabase
import kotlinx.coroutines.launch

class CarViewModel(context: Context) : ViewModel() {

    private val _cars = MutableLiveData<List<Car>>()
    val cars: LiveData<List<Car>>
        get() = _cars

    val database = Room.databaseBuilder(context, CarDatabase::class.java, "cars").build()

    init {
        getAllCars()
    }

    fun getAllCars() {
        viewModelScope.launch {
            _cars.postValue(database.carDao().getAll())
        }
    }


    fun addCar(manufacturer: String?, model: String?, manufacturing_year: String?, price: String?) {
        if (manufacturer != null && model != null && manufacturing_year != null && price != null) {
            manufacturing_year.toIntOrNull()?.let {
                val year = it
                price.toFloatOrNull()?.let {
                    val car = Car(0, manufacturer, model, year, it)
                    viewModelScope.launch {
                        database.carDao().insertCar(car)
                        getAllCars()
                    }
                }
            }
        }
    }

    fun filter(year: String?, price: String?) {
        if (!year.isNullOrEmpty()) {
            getAllNewer(year)
        } else if (!price.isNullOrEmpty()) {
            getAllCheaper(price)
        } else {
            getAllCars()
        }
    }

    private fun getAllNewer(year: String?) {
        year?.toIntOrNull()?.let {
            viewModelScope.launch {
                _cars.postValue(database.carDao().getNewer(it))
            }
        }
    }

    private fun getAllCheaper(price: String?) {
        price?.toFloatOrNull()?.let {
            viewModelScope.launch {
                _cars.postValue(database.carDao().getCheaper(it))
            }
        }
    }

    fun deleteCar(car: Car) {
        viewModelScope.launch {
            database.carDao().deleteCar(car)
            getAllCars()
        }
    }
}