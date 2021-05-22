package com.example.mainactivity

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView


class UserAdapter(val activity: Activity, val userViewModel: UserViewModel):
    ListAdapter<User, UserAdapter.ViewHolder>(UserDiffCallback()) {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name = view.findViewById<TextView>(R.id.username)
        val email = view.findViewById<TextView>(R.id.email);

        val ban = view.findViewById<Button>(R.id.button3);
        val edit = view.findViewById<Button>(R.id.button2);
    }

    class UserDiffCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.fragment_users, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)

        holder.name.text = data.username
        holder.email.text = data.email

        holder.ban.setOnClickListener {
            if (data.banned) {
                data.banned = false;

                userViewModel.unbanUser(data)
                Toast.makeText(it.context, "Vartotojas atblokuotas", Toast.LENGTH_SHORT).show();
            } else {
                data.banned = true;
                userViewModel.banUser(data)
                Toast.makeText(it.context, "Vartotojas užblokuotas", Toast.LENGTH_SHORT).show();
            }
        }

        holder.edit.setOnClickListener {
            val intent = Intent(activity, UserEdit::class.java)
            intent.putExtra("id", data.id)
            intent.putExtra("name", data.username)
            intent.putExtra("email", data.email)
            intent.putExtra("money", data.money.toString())
            activity.startActivity(intent)
        }
    }
}