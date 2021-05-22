package com.example.mainactivity

import android.R.attr.data
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mainactivity.databinding.FragmentUsersBinding


class UserAdapter(private val editListener: UserEditListener, private val banListener: UserBanListener) :
    ListAdapter<User, UserAdapter.ViewHolder>(UserDiffCallback()) {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name = view.findViewById<TextView>(R.id.username);
        val email = view.findViewById<TextView>(R.id.email);
    }

    class UserDiffCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }

    class UserEditListener(val editListener: (User: User) -> Unit) {
        fun onClick(User: User) {
            editListener(User)
        }
    }

    class UserBanListener(val banListener: (User: User) -> Unit) {
        fun onClick(User: User) {
            banListener(User)
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
    }
}