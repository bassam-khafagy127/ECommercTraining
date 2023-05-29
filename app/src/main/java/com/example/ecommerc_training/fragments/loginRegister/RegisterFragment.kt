package com.example.ecommerc_training.fragments.loginRegister

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.ecommerc_training.R
import com.example.ecommerc_training.data.model.User
import com.example.ecommerc_training.databinding.FragmentRegisterBinding
import com.example.ecommerc_training.utill.Constant.REG_TAG
import com.example.ecommerc_training.utill.RegisterValidation
import com.example.ecommerc_training.utill.Resource
import com.example.ecommerc_training.viewmodel.loginregister.RegistryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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

        binding.tvDoYouHaveAccount.setOnClickListener {
            val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
            Navigation.findNavController(it).navigate(action)
        }

        binding.apply {
            registerBtn.setOnClickListener {
                val user = User(
                    edFirstName.text.toString(),
                    edLastName.text.toString(),
                    edEmail.text.toString().trim()
                )
                val password = edPassword.text.toString()

                viewModel.createUserWithEmailPassWord(user, password)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.register.collect {
                when (it) {
                    is Resource.Loading -> {
                        Log.d(REG_TAG, "Loading")
                        binding.registerBtn.startAnimation()
                    }

                    is Resource.Error -> {
                        binding.registerBtn.revertAnimation()
                        Log.d(REG_TAG, it.message!!)
                    }

                    is Resource.Success -> {
                        binding.registerBtn.revertAnimation()
                        Log.d(REG_TAG, it.data!!.uid)
                    }

                    is Resource.Unspecified -> Log.d(REG_TAG, "Unspecified")
                }
            }

        }

        lifecycleScope.launchWhenStarted {
            viewModel.validation.collect { validation ->
                if (validation.email is RegisterValidation.Failed) {
                    withContext(Dispatchers.Main) {
                        binding.edEmail.error = validation.email.message
                    }
                }
                if (validation.password is RegisterValidation.Failed) {
                    withContext(Dispatchers.Main) {
                        binding.edPassword.error = validation.password.message
                    }
                }
            }

        }
    }
}

