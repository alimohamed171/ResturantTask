package com.example.api.repository

import com.example.api.network.ApiCalls
import javax.inject.Inject

class Repo  @Inject constructor(
    private val api: ApiCalls
) {

    suspend fun getMeals() =
        api.getMeals()
    suspend fun getCategories() =
        api.getAllCategories()
    suspend fun getMealsDetails(id:Int) =
        api.getMealsDetails(id)
}