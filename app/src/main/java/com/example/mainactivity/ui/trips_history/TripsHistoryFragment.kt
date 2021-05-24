package com.example.mainactivity.ui.trips_history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mainactivity.R

class TripsHistoryFragment : Fragment() {

    private lateinit var tripsHistoryViewModel: TripsHistoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        tripsHistoryViewModel =
            ViewModelProvider(this).get(TripsHistoryViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_trips_history, container, false)
        val textView: TextView = root.findViewById(R.id.text_notifications)
        tripsHistoryViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}