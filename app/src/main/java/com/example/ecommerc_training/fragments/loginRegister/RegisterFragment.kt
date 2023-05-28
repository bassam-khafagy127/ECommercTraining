package com.example.ecommerc_training.fragments.loginRegister

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.ecommerc_training.R
import com.example.ecommerc_training.data.model.User
import com.example.ecommerc_training.databinding.FragmentRegisterBinding
import com.example.ecommerc_training.utill.Constant.REG_TAG
import com.example.ecommerc_training.utill.Resource
import com.example.ecommerc_training.viewmodel.loginregister.RegistryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment(R.layout.fragment_register) {
    private lateinit var binding: FragmentRegisterBinding
    private val viewModel by viewModels<RegistryViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            registerBtn.setOnClickListener {

                registerBtn.startAnimation()
                val user = User(
                    edFirstName.text.toString(),
                    edLastName.text.toString(),
                    edEmail.text.toString().trim()
                )
                val password = edPassword.text.toString().trim()

                viewModel.createUserWithEmailPassWord(user, password)
            }

        }
        lifecycleScope.launchWhenStarted {
            viewModel.register.collect {
                when (it) {

                    is Resource.Error -> {
                        binding.registerBtn.revertAnimation()
                        Log.d(REG_TAG, it.message!!)
                    }
                    is Resource.Loading -> Log.d(REG_TAG, "Loading")
                    is Resource.Success -> {
                        binding.registerBtn.revertAnimation()
                        Log.d(REG_TAG, it.data!!.uid)
                    }
                    is Resource.Unspecified -> Log.d(REG_TAG, "Unspecified")

                }
            }

        }
    }
}

