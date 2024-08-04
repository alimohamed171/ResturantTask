package com.example.api.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.api.R
import com.example.api.adapter.CategoryAdapter
import com.example.api.adapter.MealsAdapter
import com.example.api.databinding.FragmentHomeBinding
import com.example.api.utill.Resource
import com.example.restauranttask.util.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    var _binding : FragmentHomeBinding? = null
    val binding get() = _binding!!
    val viewModel : HomeViewModel by viewModels()
    lateinit var mealsAdapter :MealsAdapter
    lateinit var categoryAdapter: CategoryAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)
        observe()
        setupAdapter()


    }



    private fun setupAdapter() {
        mealsAdapter = MealsAdapter()
        mealsAdapter.setOnItemClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailsFragment(it.id))

        }
        mealsAdapter.setOnSaveClickListener {
        }
        categoryAdapter = CategoryAdapter()
        categoryAdapter.setOnItemClickListener {

        }
        binding.rvProduct.apply {
            adapter = mealsAdapter
        }
        binding.rvCategory.apply {
            adapter = categoryAdapter
        }
    }

    private fun observe() {

        getCategories()
        getMeals()
    }

    private fun getCategories() {
        viewModel.getCategories()
        viewModel.mutableCategoriesLiveData.observe(viewLifecycleOwner){ response ->
            when(response){
                is Resource.Error -> {
                    showToast("fail")
                }
                is Resource.Success -> {
                    response.data?.let {data ->
                        categoryAdapter.differ.submitList(data )
                       // showToast(data.get(1).name)

                    }
                }
            }

        }
    }

    private fun getMeals() {
        viewModel.getMeals()
        viewModel.mutableMealsLiveData.observe(viewLifecycleOwner){ response ->
            when(response){
                is Resource.Error -> {
                    showToast("fail")
                }
                is Resource.Success -> {
                    response.data?.let {data ->
                        mealsAdapter.differ.submitList(data )
                       // showToast(data.get(1).name)

                    }
                }
            }

        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}