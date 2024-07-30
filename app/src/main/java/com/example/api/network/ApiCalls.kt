package com.example.api.network

import com.example.api.model.ModelCategories
import com.example.api.model.ModelProduct
import com.example.api.model.ModelProductDetails
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiCalls {

    @GET("categories.php")
    suspend fun getAllCategories()
            : ModelCategories

    @GET("products.php")
    suspend fun getMeals(): ModelProduct

    @GET("product_details.php")
    suspend fun getMealsDetails(@Query("id") id: Int): ModelProductDetails


}