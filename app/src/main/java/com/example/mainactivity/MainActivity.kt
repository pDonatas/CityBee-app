package com.example.mainactivity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.mainactivity.ui.login.Auth
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pref = applicationContext
            .getSharedPreferences("cityBee", 0)

        println("Checking if user logged in")
        println(pref.all)
        if (!pref.contains("user_id")) {
            println("Showing log in form")
            val intent = Intent(this, Auth::class.java)
            startActivity(intent)
            finish()
        }

        val intent = Intent(this, Admin::class.java)
        val onClickListener: View.OnClickListener = View.OnClickListener { startActivity(intent) }

        val button: Button = findViewById(R.id.admin_btn)
        button.setOnClickListener(onClickListener)


        val bottomNavigation: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        bottomNavigation.setupWithNavController(navController)

        val logout = findViewById<Button>(R.id.logout)

        logout.setOnClickListener {
            logout()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    fun logout() {
        val pref = applicationContext
                .getSharedPreferences("cityBee", 0)
        pref.edit().clear().apply()
    }
}