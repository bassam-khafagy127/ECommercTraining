package com.example.ecommerc_training.fragments.loginRegister

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.ecommerc_training.R
import com.example.ecommerc_training.databinding.FragmentAccountOptionsBinding

class AccountOptionsFragment : Fragment(R.layout.fragment_account_options) {
    private lateinit var binding: FragmentAccountOptionsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAccountOptionsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loginBtn.setOnClickListener {
            val action =
                AccountOptionsFragmentDirections.actionAccountOptionsFragmentToLoginFragment()
            Navigation.findNavController(it).navigate(action)
        }
        binding.registerBtn.setOnClickListener {
            val action =
                AccountOptionsFragmentDirections.actionAccountOptionsFragmentToRegisterFragment()
            Navigation.findNavController(it).navigate(action)
        }
    }
}