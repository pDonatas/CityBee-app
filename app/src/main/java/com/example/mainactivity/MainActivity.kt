package com.example.mainactivity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intent = Intent(this, Admin::class.java)
        val onClickListener: View.OnClickListener = View.OnClickListener { startActivity(intent) }

        val button: Button = findViewById(R.id.admin_btn)
        button.setOnClickListener(onClickListener)
    }
}