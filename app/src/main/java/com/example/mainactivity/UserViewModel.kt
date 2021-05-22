package com.example.mainactivity

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.mainactivity.CarDatabase
import kotlinx.coroutines.launch

class UserViewModel(context: Context) : ViewModel() {

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>>
        get() = _users

    val database = Room.databaseBuilder(context, UserDatabase::class.java, "users").build()

    init {
        getAllUsers()
    }

    private fun getAllUsers() {
        viewModelScope.launch {
            _users.postValue(database.userDao().getAll())
        }
    }

    fun addUser(username: String?, email: String?, password: String?, money: String?) {
        val text = String.format("%s, %s, %s, %s", username, email, password, money);
        println(text);
        if (username != null && email != null && password != null && money != null) {
            money.toDoubleOrNull()?.let {
                val user = User(0, username, email, password, it)
                viewModelScope.launch {
                    database.userDao().insertUser(user)
                    getAllUsers()
                }
            }
        }
    }

    fun filter(username: String?) {
        if (!username.isNullOrEmpty()) {
            getByName(username)
        } else {
            getAllUsers()
        }
    }

    private fun getByName(username: String?) {
        if (username != null) {
            viewModelScope.launch {
                _users.postValue(database.userDao().getByName(username))
            }
        }
    }

    fun deleteUser(user: User) {
        viewModelScope.launch {
            database.userDao().deleteUser(user)
            getAllUsers()
        }
    }

    fun updateUser(user: User) {
        viewModelScope.launch {
            database.userDao().updateUser(user)
            getAllUsers()
        }
    }

    fun banUser(user: User) {
        viewModelScope.launch {
            database.userDao().banUser(user.id)
            getAllUsers()
        }
    }
}