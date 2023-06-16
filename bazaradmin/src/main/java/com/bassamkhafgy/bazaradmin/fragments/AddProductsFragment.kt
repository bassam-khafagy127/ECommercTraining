package com.bassamkhafgy.bazaradmin.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bassamkhafgy.bazaradmin.R
import com.bassamkhafgy.bazaradmin.databinding.FragmentAddProductsBinding

class AddProductsFragment : Fragment(R.layout.fragment_add_products) {
    private lateinit var binding: FragmentAddProductsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddProductsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}