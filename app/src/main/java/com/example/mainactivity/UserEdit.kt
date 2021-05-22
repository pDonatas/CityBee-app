package com.example.mainactivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.viewModels
import com.example.mainactivity.ui.login.Auth

class UserEdit : AppCompatActivity() {

    val viewModel: UserViewModel by viewModels { UserViewModelFactory(applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_edit)

        val username = findViewById<TextView>(R.id.edit_user_name)
        val email = findViewById<TextView>(R.id.edit_user_email)
        val money = findViewById<TextView>(R.id.edit_user_money)
        val password = findViewById<TextView>(R.id.edit_user_password)
        val submit = findViewById<Button>(R.id.edit_user_submit)

        username.text = intent.getStringExtra("name")
        email.text = intent.getStringExtra("email")
        money.text = intent.getStringExtra("money").toString()

        submit.setOnClickListener {
            val id = intent.getIntExtra("id", 0)
            updateUserData(username, email, password, money, id)
            val intent = Intent(this, Admin::class.java)
            startActivity(intent)
            finish()

            Toast.makeText(it.context, "Vartotojas redaguotas sėkmingai", Toast.LENGTH_SHORT).show();
        }
    }

    private fun updateUserData(username: TextView?, email: TextView?, password: TextView?, money: TextView?, id: Int) {
        val name = username?.text.toString()
        val el = email?.text.toString()
        val cash = money?.text.toString()

        viewModel.editUser(id, name, el, cash)

        if (password != null) {
            viewModel.updateUserPassword(id, password.text.toString())
        }
    }
}