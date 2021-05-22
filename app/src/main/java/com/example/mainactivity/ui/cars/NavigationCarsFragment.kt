package com.example.mainactivity.ui.cars


import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import com.example.mainactivity.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class NavigationCarsFragment : Fragment(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_navigation_cars, container, false)

        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        return rootView
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val car1 = LatLng(54.9057034,23.9658789)
        val car2 = LatLng(54.9057251,23.9623187)
        val car3 = LatLng(54.9054983,23.970993)
        val car4 = LatLng(54.899713, 23.967215)
        val car5 = LatLng(54.9068502,23.9641626)
        mMap.addMarker(MarkerOptions().position(car1)
            .title("Car1")
            .icon(vectorToBitmap(R.drawable.ic_baseline_directions_car_24)))
        mMap.addMarker(MarkerOptions().position(car2)
            .title("Car2")
            .icon(vectorToBitmap(R.drawable.ic_baseline_directions_car_24)))
        mMap.addMarker(MarkerOptions().position(car3)
            .title("Car3")
            .icon(vectorToBitmap(R.drawable.ic_baseline_directions_car_24)))
        mMap.addMarker(MarkerOptions().position(car4)
            .title("Car4")
            .icon(vectorToBitmap(R.drawable.ic_baseline_directions_car_24)))
        mMap.addMarker(MarkerOptions().position(car5)
            .title("Car5")
            .icon(vectorToBitmap(R.drawable.ic_baseline_directions_car_24)))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(car1, 15f))

    }
    private fun vectorToBitmap(@DrawableRes id: Int): BitmapDescriptor? {
        val vectorDrawable = ResourcesCompat.getDrawable(resources, id, null)
        val bitmap = Bitmap.createBitmap(
            vectorDrawable!!.intrinsicWidth,
            vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }




}
