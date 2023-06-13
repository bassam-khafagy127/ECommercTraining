package com.example.ecommerc_training.fragments.loginRegister

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.ecommerc_training.R
import com.example.ecommerc_training.activites.ShoppingActivity
import com.example.ecommerc_training.databinding.FragmentIntroductionBinding
import com.example.ecommerc_training.utill.NAVIGATION.ACCOUNT_OPTION_FRAGMENT
import com.example.ecommerc_training.utill.NAVIGATION.SHOPPING_ACTIVITY
import com.example.ecommerc_training.viewmodel.introduction.IntroductionViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class IntroductionFragment : Fragment(R.layout.fragment_introduction) {
    private lateinit var binding: FragmentIntroductionBinding
    private val viewModel by viewModels<IntroductionViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentIntroductionBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenCreated {
            viewModel.navigation.collect {
                when (it) {
                    SHOPPING_ACTIVITY -> {
                        val intent =
                            Intent(requireActivity(), ShoppingActivity::class.java).also { intent ->
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            }
                        startActivity(intent)
                    }

                    ACCOUNT_OPTION_FRAGMENT -> {
                        findNavController().navigate(it)
                    }
                }
            }
        }

        binding.startBtn.setOnClickListener {
            findNavController().navigate(R.id.action_introductionFragment_to_accountOptionsFragment)
            viewModel.isButtonClickedFunction()
        }
    }
}
