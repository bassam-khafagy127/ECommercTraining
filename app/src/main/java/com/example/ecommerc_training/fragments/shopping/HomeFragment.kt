package com.example.ecommerc_training.fragments.shopping

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.ecommerc_training.R
import com.example.ecommerc_training.adapters.ViewPager2Adapter
import com.example.ecommerc_training.databinding.FragmentHomeBinding
import com.example.ecommerc_training.fragments.categories.AccessoriesFragment
import com.example.ecommerc_training.fragments.categories.ChairFragment
import com.example.ecommerc_training.fragments.categories.FurnitureFragment
import com.example.ecommerc_training.fragments.categories.MainCategoryFragment
import com.example.ecommerc_training.fragments.categories.TablesFragment
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentsCategories: List<Fragment> = arrayListOf(
            MainCategoryFragment(),
            ChairFragment(),
            FurnitureFragment(),
            AccessoriesFragment(),
            TablesFragment()
        )

        val viewPager2Adapter =
            ViewPager2Adapter(fragmentsCategories, childFragmentManager, lifecycle)

        binding.homeViewPager.adapter = viewPager2Adapter

        TabLayoutMediator(binding.homeTabLayout, binding.homeViewPager) { tap, position ->
            when (position) {
                0 -> tap.text = "Home"
                1 -> tap.text = "Chair"
                2 -> tap.text = "Furniture"
                3 -> tap.text = "Accessories"
                4 -> tap.text = "Tables"
            }
        }.attach()
    }
}