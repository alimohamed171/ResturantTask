package com.example.api.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.api.model.CategoryData
import com.example.api.model.Data
import com.example.api.repository.Repo
import com.example.api.utill.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repo:Repo
):ViewModel() {

    private val _mutableMealsLiveData =  MutableLiveData<Resource<List<Data>?>>()

    val mutableMealsLiveData get() = _mutableMealsLiveData


  private val _mutableCategoriesLiveData =  MutableLiveData<Resource<List<CategoryData>?>>()

    val mutableCategoriesLiveData get() = _mutableCategoriesLiveData


    fun getMeals(){
        viewModelScope.launch (Dispatchers.IO){
            try {
                val response = repo.getMeals()
                if (response.status){
                    _mutableMealsLiveData.postValue(Resource.Success(response.data))
                }else{
                    _mutableMealsLiveData.postValue(Resource.Error("error in apis"))
                }
            }catch (e: Exception){
                _mutableMealsLiveData.postValue(Resource.Error("An error occurred: ${e.message}"))
            }
        }
    }

     fun getCategories(){
        viewModelScope.launch (Dispatchers.IO){
            try {
                val response = repo.getCategories()
                if (response.status){
                    _mutableCategoriesLiveData.postValue(Resource.Success(response.data))
                }else{
                    _mutableCategoriesLiveData.postValue(Resource.Error("error in apis"))
                }
            }catch (e: Exception){
                _mutableCategoriesLiveData.postValue(Resource.Error("An error occurred: ${e.message}"))
            }
        }
    }



}