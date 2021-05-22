package com.example.mainactivity

import android.content.Intent
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

        if (!pref.contains("user_id")) {
            val intent = Intent(this, Auth::class.java)
            startActivity(intent)
        }

        val intent = Intent(this, Admin::class.java)
        val onClickListener: View.OnClickListener = View.OnClickListener { startActivity(intent) }

        val button: Button = findViewById(R.id.admin_btn)
        button.setOnClickListener(onClickListener)


        val bottomNavigation: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        bottomNavigation.setupWithNavController(navController)

    }


}