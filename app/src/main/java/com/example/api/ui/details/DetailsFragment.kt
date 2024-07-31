package com.example.api.ui.details

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.api.R
import com.example.api.databinding.FragmentDetailsBinding
import com.example.api.model.ModelPhone
import com.example.api.model.PhoneData
import com.example.api.model.ProductDetailsData
import com.example.api.ui.home.HomeViewModel
import com.example.api.utill.Resource
import com.example.restauranttask.util.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details) {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private val args: DetailsFragmentArgs by navArgs()
    val viewModel : DetailsViewmodel by viewModels()
    lateinit var phone: PhoneData

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDetailsBinding.bind(view)
        val mealId = args.id
        observe(mealId)
        onClicks()
    }


    private fun onClicks() {
        binding.btnBack.setOnClickListener{
            findNavController().navigateUp()
        }
        binding.imageButton2.setOnClickListener {
            openDialer(phone.phone)
        }
        binding.imageButton.setOnClickListener {
            openWhatsApp(phone.whatsapp)
        }
    }

    private fun observe(id : Int) {

        getMealDetails(id)
        getPhone()
    }

    private fun getPhone() {
        viewModel.getPhoneNumber()
        viewModel.mutablePhoneLiveData.observe(viewLifecycleOwner){ response ->
            when(response){
                is Resource.Error -> {
                    showToast("fail")
                }
                is Resource.Success -> {
                    response.data?.let {data ->
                     phone = data
                    }
                }
            }
        }

    }


    private fun openDialer(phoneNumber: String) {
        val dialIntent = Intent(Intent.ACTION_DIAL)
        dialIntent.data = Uri.parse("tel:$phoneNumber")
        startActivity(dialIntent)
    }
    private fun openWhatsApp(phoneNumber: String) {
        val uri = Uri.parse("https://wa.me/$phoneNumber")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.setPackage("com.whatsapp")
        try {
            startActivity(intent)
        } catch (e: Exception) {
            // Handle exception if WhatsApp is not installed
            e.printStackTrace()
        }
    }

    private fun getMealDetails(id: Int) {
        viewModel.getMealDetails(id)
        viewModel.mutableMealLiveData.observe(viewLifecycleOwner){ response ->
            when(response){
                is Resource.Error -> {
                    showToast("fail")
                }
                is Resource.Success -> {
                    response.data?.let {data ->
                        setupUI(data)
                    }
                }
            }

        }

    }


    private fun setupUI(meal:ProductDetailsData) {

        val price =  meal.price.toFloat() - meal.discount.toFloat()

        binding.detailsName.text = meal.name
        binding.detailsDiscountedPrice.text = price.toString()
        binding.detailsOriginalPrice.text = meal.price
        binding.detailsRating.rating = meal.rate.toFloat()
        binding.restaurantName.text = meal.restaurant_name

        Glide.with(this)
            .load(meal.product_image)
            .into(binding.detailsImage)
        Glide.with(this)
            .load(meal.restaurant_image)
            .into(binding.imageView3)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}