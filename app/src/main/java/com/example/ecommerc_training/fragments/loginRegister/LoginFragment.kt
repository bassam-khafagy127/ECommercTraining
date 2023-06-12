package com.example.ecommerc_training.fragments.loginRegister

import android.content.Intent
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
import com.example.ecommerc_training.activites.ShoppingActivity
import com.example.ecommerc_training.databinding.FragmentLoginBinding
import com.example.ecommerc_training.fragments.dialog.setBottomSheetDialog
import com.example.ecommerc_training.utill.Constant
import com.example.ecommerc_training.utill.RegisterValidation
import com.example.ecommerc_training.utill.Resource
import com.example.ecommerc_training.viewmodel.loginregister.LoginViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@AndroidEntryPoint
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

        binding.tvDonHaveAccount.setOnClickListener {
            val action =
                LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            Navigation.findNavController(it).navigate(action)
        }

        binding.tvForgotPassword.setOnClickListener {
            setBottomSheetDialog { email ->
                viewModel.resetPassword(email)
            }
        }
        binding.apply {
            loginBtn.setOnClickListener {
                val email = edEmail.text.toString().trim()
                val password = edPassword.text.toString()
                viewModel.signInWithEmailPassWord(email, password)
            }

        }
        lifecycleScope.launchWhenStarted {
            viewModel.login.collect {
                when (it) {
                    is Resource.Error -> {
                        binding.loginBtn.revertAnimation()
                      Log.d(Constant.REG_TAG, it.message!!)
                        Snackbar.make(requireView(), "${it.message}", Snackbar.LENGTH_LONG)
                            .show()
                    }

                    is Resource.Loading -> {
                        binding.loginBtn.startAnimation()
                        Log.d(Constant.REG_TAG, "Loading")
                    }

                    is Resource.Success -> {
                        binding.loginBtn.revertAnimation()
                        Log.d(Constant.REG_TAG, it.data!!.uid)
                        startShoppingActivity()
                    }

                    is Resource.Unspecified -> Log.d(Constant.REG_TAG, "Unspecified")

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

        lifecycleScope.launchWhenStarted {

            viewModel.resetPassword.collect {
                when (it) {
                    is Resource.Error -> {
                        Snackbar.make(requireView(), "${it.message}", Snackbar.LENGTH_LONG)
                            .show()
                    }

                    is Resource.Loading -> {
                        Log.d(Constant.LOG_TAG, "Loading")
                    }

                    is Resource.Success -> {
                        Snackbar.make(requireView(), "Link was sent to email", Snackbar.LENGTH_LONG)
                            .show()
                    }

                    is Resource.Unspecified -> Log.d(Constant.REG_TAG, "Unspecified")

                }
            }
        }


    }

    private fun startShoppingActivity() {
        val intent = Intent(requireActivity(), ShoppingActivity::class.java).also { intent ->
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        startActivity(intent)

    }
}