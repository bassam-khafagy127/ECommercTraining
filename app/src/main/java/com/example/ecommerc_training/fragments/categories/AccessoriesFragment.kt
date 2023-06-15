package com.example.ecommerc_training.fragments.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.ecommerc_training.R
import com.example.ecommerc_training.databinding.FragmentAccessoriesBinding

class AccessoriesFragment : Fragment(R.layout.fragment_accessories) {
    private lateinit var binding: FragmentAccessoriesBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAccessoriesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}