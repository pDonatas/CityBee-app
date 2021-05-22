package com.example.mainactivity

import android.content.Context
import android.content.SharedPreferences
import android.util.Patterns
import androidx.lifecycle.*
import androidx.room.Room
import com.example.mainactivity.data.Result
import com.example.mainactivity.ui.login.LoggedInUserView
import com.example.mainactivity.ui.login.LoginFormState
import com.example.mainactivity.ui.login.LoginResult
import kotlinx.coroutines.launch
import java.security.MessageDigest

class UserViewModel(context: Context) : ViewModel() {

    val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>>
        get() = _users

    val database = Room.databaseBuilder(context, UserDatabase::class.java, "users").allowMainThreadQueries().build()

    init {
        getAllUsers()
    }

    private fun getAllUsers() {
        viewModelScope.launch {
            _users.postValue(database.userDao().getAll())
        }
    }

    fun userExists(username: String?): Boolean {
        if (username == null) return false;

        return database.userDao().userExists(username);
    }

    fun addUser(username: String?, email: String?, password: String?, money: String?): Boolean {
        val exists = userExists(username);
        if (!exists) {
            if (username != null && email != null && password != null && money != null) {
                money.toDoubleOrNull()?.let {
                    val user = User(0, username, email, password, it)
                    viewModelScope.launch {
                        database.userDao().insertUser(user)
                        getAllUsers()
                    }
                    return true;
                }
            }

            return false;
        }

        return false;
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

    fun unbanUser(user: User) {
        viewModelScope.launch {
            database.userDao().unbanUser(user.id)
            getAllUsers()
        }
    }

    fun login(username: String, password: String) {
        // can be launched in a separate asynchronous job
        val result = database.login(username, hashString(password, "SHA-256"))

        if (result is User) {
            _loginResult.value =
                LoginResult(
                    success = LoggedInUserView(
                        id = result.id,
                        username = result.username,
                        email = result.email,
                        money = result.money,
                        banned = result.banned
                    )
                )
        } else {
            _loginResult.value = LoginResult(error = result.toString())
        }
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    fun registerDataChanged(username: String, password: String, name: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        }  else if (!isNameValid(name)) {
            _loginForm.value = LoginFormState(nameError = R.string.invalid_name)
        }else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }

    private fun isNameValid(username: String): Boolean {
        return username.isNotBlank()
    }

    fun register(email: String, password: String, name: String): Boolean {
        return addUser(name, email, hashString(password, "SHA-256"), 0.toString())
    }

    fun editUser(id: Int, username: String?, email: String?, money: String?) {
        viewModelScope.launch {
            database.userDao().editUser(id, username, email, money)
            getAllUsers()
        }
    }

    fun updateUserPassword(id: Int, password: String) {
        val pass = hashString(password, "SHA-256")

        viewModelScope.launch {
            database.userDao().changeUserPassword(id, pass)
            getAllUsers()
        }
    }

    private fun hashString(input: String, algorithm: String): String    {
        return MessageDigest.getInstance(algorithm)
                .digest(input.toByteArray())
                .fold("", { str, it -> str + "%02x".format(it) })
    }
}