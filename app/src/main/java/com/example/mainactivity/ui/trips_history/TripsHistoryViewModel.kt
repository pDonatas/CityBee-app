package com.example.mainactivity.ui.trips_history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TripsHistoryViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is trips history fragment"
    }
    val text: LiveData<String> = _text
}