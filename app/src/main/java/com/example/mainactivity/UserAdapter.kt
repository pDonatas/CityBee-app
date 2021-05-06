package com.example.mainactivity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mainactivity.databinding.ItemUserBinding

class UserAdapter(val clickListener: UserClickListener) :
    ListAdapter<User, UserAdapter.ViewHolder>(UserDiffCallback()) {

    class ViewHolder(val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User, clickListener: UserClickListener) {
            binding.user = user
            binding.clickListener = clickListener
        }
    }

    class UserDiffCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }

    class UserClickListener(val clickListener: (User: User) -> Unit) {
        fun onClick(User: User) {
            clickListener(User)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemUserBinding
                .inflate(
                    LayoutInflater
                        .from(parent.context)
                )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }


}