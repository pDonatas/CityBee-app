package com.example.mainactivity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mainactivity.*
import com.example.mainactivity.ui.login.Auth
import com.example.mainactivity.ui.login.LoggedInUserView
import com.example.mainactivity.ui.login.LoginFormState

class Registration : AppCompatActivity() {

    private lateinit var registerViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_registration)

        val username = findViewById<EditText>(R.id.email_register)
        val password = findViewById<EditText>(R.id.password_register)
        val name = findViewById<EditText>(R.id.name_register)
        val register = findViewById<Button>(R.id.register_btn)
        val login = findViewById<Button>(R.id.login_btn)
        login.isEnabled = true
        val loading = findViewById<ProgressBar>(R.id.loading)

        registerViewModel = ViewModelProvider(this, UserViewModelFactory(applicationContext))
            .get(UserViewModel::class.java)

        registerViewModel.loginFormState.observe(this@Registration, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            register.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }
        })

        username.afterTextChangedRegister {
            registerViewModel.registerDataChanged(
                username.text.toString(),
                password.text.toString(),
                name.text.toString()
            )
        }

        name.afterTextChangedRegister {
            registerViewModel.registerDataChanged(
                username.text.toString(),
                password.text.toString(),
                name.text.toString()
            )
        }

        password.apply {
            afterTextChangedRegister {
                registerViewModel.registerDataChanged(
                    username.text.toString(),
                    password.text.toString(),
                    name.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        registerViewModel.register(
                            username.text.toString(),
                            password.text.toString(),
                            name.text.toString()
                        )
                }
                false
            }

            register.setOnClickListener {
                loading.visibility = View.VISIBLE
                if (registerViewModel.register(
                    username.text.toString(),
                    password.text.toString(),
                    name.text.toString()
                )) {
                    Toast.makeText(applicationContext, "Registracija sėkminga", Toast.LENGTH_SHORT).show();
                    loading.visibility = View.GONE
                    val intent = Intent(applicationContext, Auth::class.java)
                    startActivity(intent)
                    finish();
                } else {
                    Toast.makeText(applicationContext, "Toks vartotojas jau egzistuoja", Toast.LENGTH_SHORT).show();
                    loading.visibility = View.GONE
                }
            }

            login.setOnClickListener {
                val intent = Intent(applicationContext, Auth::class.java)
                startActivity(intent)
                finish();
            }
        }
    }

    @SuppressLint("CommitPrefEdits")
    private fun updateUiWithUser(model: LoggedInUserView) {
        val pref = applicationContext
            .getSharedPreferences("cityBee", 0)

        val editor = pref.edit();

        editor.putInt("user", model.id)
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, "Neteisingas vartotojo vardas ir/arba slaptažodis", Toast.LENGTH_SHORT).show();
        registerViewModel._loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
    }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChangedRegister(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}