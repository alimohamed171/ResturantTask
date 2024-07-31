package com.example.api.ui.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.api.model.Data
import com.example.api.model.PhoneData
import com.example.api.model.ProductDetailsData
import com.example.api.repository.Repo
import com.example.api.utill.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject


@HiltViewModel
class DetailsViewmodel @Inject constructor(
    private val repo: Repo
): ViewModel() {

    private val _mutableMealLiveData =  MutableLiveData<Resource<ProductDetailsData?>>()

    val mutableMealLiveData get() = _mutableMealLiveData


    private val _mutablePhoneLiveData =  MutableLiveData<Resource<PhoneData?>>()
    val mutablePhoneLiveData get() = _mutablePhoneLiveData
    fun getPhoneNumber(){
        viewModelScope.launch (Dispatchers.IO){
            try {
                val response = repo.getPhoneNumber()
                if (response.status){
                    _mutablePhoneLiveData.postValue(Resource.Success(response.data))
                }else{
                    _mutablePhoneLiveData.postValue(Resource.Error("error in apis"))
                }
            }catch (e: Exception){
                _mutablePhoneLiveData.postValue(Resource.Error("An error occurred: ${e.message}"))
            }
        }
    }


    fun getMealDetails(id:Int){
        viewModelScope.launch (Dispatchers.IO){
            try {
                val response = repo.getMealsDetails(id)
                if (response.status){
                    _mutableMealLiveData.postValue(Resource.Success(response.data))
                }else{
                    _mutableMealLiveData.postValue(Resource.Error("error in apis"))
                }
            }catch (e: Exception){
                _mutableMealLiveData.postValue(Resource.Error("An error occurred: ${e.message}"))
            }
        }
    }
}
