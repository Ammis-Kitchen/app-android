package com.ammiskitchen.ammiskitchenapp.presentation.ui.mainfeed.menu

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ammiskitchen.ammiskitchenapp.data.models.response.ResponseCuisines
import com.ammiskitchen.ammiskitchenapp.data.models.response.ResponseSubCategory
import com.ammiskitchen.ammiskitchenapp.data.util.Resource
import com.ammiskitchen.ammiskitchenapp.domain.usecase.menu.GetCuisinesUseCase
import com.ammiskitchen.ammiskitchenapp.domain.usecase.menu.GetMenuUseCase
import com.ammiskitchen.ammiskitchenapp.domain.usecase.menu.GetSubCuisinesUseCase
import kotlinx.coroutines.launch
import java.lang.Exception

class MenuViewModel(
    private val app: Application,
    private val getCuisinesUseCase: GetCuisinesUseCase,
    private val getMenuUseCase: GetMenuUseCase,
    private val getSubCuisinesUseCase: GetSubCuisinesUseCase
) : AndroidViewModel(app) {

    private var _responseCuisines = MutableLiveData<Resource<ResponseCuisines>>()
    val responseCuisines get() = _responseCuisines

    private var _responseSubCuisines = MutableLiveData<Resource<ResponseSubCategory>>()
    val responseSubCuisines get() = _responseSubCuisines

    // Internet availability code
    private fun isNetworkAvailable(context: Context?):Boolean{
        if (context == null) return false
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false
    }

    //get cuisines
    fun getCuisinesList() {
        viewModelScope.launch {
            _responseCuisines.postValue(Resource.Loading())
            try {
                if(isNetworkAvailable(app)) {
                    val cuisines = getCuisinesUseCase.execute()
                    _responseCuisines.postValue(cuisines)
                } else {
                    _responseCuisines.postValue(Resource.Error("Internet is not available."))
                }
            } catch (e: Exception) {
                _responseCuisines.postValue(Resource.Error(e.message.toString()))
            }
        }
    }

    fun getSubCuisinesList(cuisine: String) {
        viewModelScope.launch {
            _responseSubCuisines.postValue(Resource.Loading())
            try {
                if(isNetworkAvailable(app)) {
                    val cuisines = getSubCuisinesUseCase.execute(cuisine)
                    _responseSubCuisines.postValue(cuisines)
                } else {
                    _responseSubCuisines.postValue(Resource.Error("Internet is not available."))
                }
            } catch (e: Exception) {
                _responseSubCuisines.postValue(Resource.Error(e.message.toString()))
            }
        }
    }
}