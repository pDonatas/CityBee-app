package com.example.mainactivity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.example.mainactivity.databinding.FragmentCarsBinding
import com.example.mainactivity.databinding.FragmentUsersListBinding
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay

@Suppress("DEPRECATION")
class UsersFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentUsersListBinding.inflate(inflater)

        val viewModel: UserViewModel by viewModels { UserViewModelFactory(requireContext()) }

        binding.viewmodel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val adapter = UserAdapter(UserAdapter.UserEditListener {
            viewModel.updateUser(it)
        }, UserAdapter.UserBanListener {
            viewModel.banUser(it)
        })

        binding.list.adapter = adapter

        viewModel.users.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        binding.button5.setOnClickListener {
            viewModel.addUser(
                binding.userName4.text.toString(),
                binding.userEmail.text.toString(),
                binding.userPassword.text.toString(),
                0.toString()
            )
        }

        return binding.root
    }

}