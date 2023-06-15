package com.example.ecommerc_training.fragments.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.ecommerc_training.R
import com.example.ecommerc_training.databinding.FragmentFurnituerBinding

class FurnitureFragment : Fragment(R.layout.fragment_furnituer) {
    private lateinit var binding: FragmentFurnituerBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFurnituerBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}