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
import com.example.ecommerc_training.databinding.FragmentLoginBinding
import com.example.ecommerc_training.utill.Constant
import com.example.ecommerc_training.utill.Resource
import com.example.ecommerc_training.viewmodel.loginregister.LoginViewModel

class LoginFragment : Fragment(R.layout.fragment_login) {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel by viewModels<LoginViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            loginBtn.setOnClickListener {
                loginBtn.startAnimation()

            }

        }
        lifecycleScope.launchWhenStarted {
            viewModel.login.collect {
                when (it) {
                    is Resource.Error -> {
                        binding.loginBtn.revertAnimation()
                        Log.d(Constant.REG_TAG, it.message!!)
                    }

                    is Resource.Loading -> Log.d(Constant.REG_TAG, "Loading")
                    is Resource.Success -> {
                        binding.loginBtn.revertAnimation()
                        Log.d(Constant.REG_TAG, it.data!!.uid)
                    }

                    is Resource.Unspecified -> Log.d(Constant.REG_TAG, "Unspecified")

                }
            }
        }
    }
}