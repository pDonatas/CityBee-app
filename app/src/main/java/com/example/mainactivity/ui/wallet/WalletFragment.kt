package com.example.mainactivity.ui.wallet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import com.example.mainactivity.CarViewModel
import com.example.mainactivity.R
import com.example.mainactivity.User
import com.example.mainactivity.UserViewModel
import com.example.mainactivity.databinding.FragmentUsersListBinding


class WalletFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val view = inflater.inflate(R.layout.fragment_wallet, container, false)
        val button = view.findViewById<Button>(R.id.add_money_btn)
        val money = view.findViewById<TextView>(R.id.money)
        val add = view.findViewById<EditText>(R.id.add_money_text)

        button.setOnClickListener {
            val num1 = money.text.toString().toFloat()
            val num2 = add.text.toString().toFloat()
            val result = num1 + num2
            money.text = result.toString()
        }


        return view
    }


}