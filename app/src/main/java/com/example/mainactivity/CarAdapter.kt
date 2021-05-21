package com.example.mainactivity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mainactivity.databinding.ItemCarBinding

class CarAdapter(val clickListener: CarClickListener) :
    ListAdapter<Car, CarAdapter.ViewHolder>(CarDiffCallback()) {

    class ViewHolder(val binding: ItemCarBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(car: Car, clickListener: CarClickListener) {
            binding.car = car
            binding.clickListener = clickListener
        }
    }

    class CarDiffCallback : DiffUtil.ItemCallback<Car>() {
        override fun areItemsTheSame(oldItem: Car, newItem: Car): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Car, newItem: Car): Boolean {
            return oldItem == newItem
        }
    }

    class CarClickListener(val clickListener: (car: Car) -> Unit) {
        fun onClick(car: Car) {
            clickListener(car)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCarBinding
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