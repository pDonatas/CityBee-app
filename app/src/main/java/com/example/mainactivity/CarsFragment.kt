package com.example.mainactivity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.example.mainactivity.databinding.FragmentCarsBinding

@Suppress("DEPRECATION")
class CarsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentCarsBinding.inflate(inflater)

        val viewModel: CarViewModel by viewModels { CarViewModelFactory(requireContext()) }

        binding.viewmodel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val adapter = CarAdapter(CarAdapter.CarClickListener {
            viewModel.deleteCar(it)
        })
        binding.carRecyclerView.adapter = adapter

        viewModel.cars.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        binding.addCar.setOnClickListener {
            viewModel.addCar(
                binding.carManufacturerField.text.toString(),
                binding.carModel.text.toString(),
                binding.manufactureYear.text.toString(),
                binding.manufacturePrice.text.toString()
            )
        }

        binding.filterBtn.setOnClickListener {
            viewModel.filter(
                binding.filterYear.text.toString(),
                binding.filterPrice.text.toString()
            )
        }

        return binding.root
    }

}