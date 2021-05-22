package com.example.mainactivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.mainactivity.ui.login.Auth
import androidx.appcompat.app.ActionBar
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    lateinit var toolbar: ActionBar
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

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        toolbar = supportActionBar!!
        val bottomNavigation: BottomNavigationView = findViewById(R.id.nav_view)

    }
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val kaunas = LatLng(54.9057034,23.9658789)
        mMap.addMarker(MarkerOptions().position(kaunas).title("Marker in Kaunas"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(kaunas, 20f))

    }
}