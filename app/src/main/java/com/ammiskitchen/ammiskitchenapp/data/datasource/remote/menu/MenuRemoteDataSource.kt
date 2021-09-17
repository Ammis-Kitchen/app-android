package com.ammiskitchen.ammiskitchenapp.data.datasource.remote.menu

import com.ammiskitchen.ammiskitchenapp.data.models.response.ResponseCuisines
import com.ammiskitchen.ammiskitchenapp.data.models.response.ResponseMenu
import com.ammiskitchen.ammiskitchenapp.data.models.response.ResponseSubCategory
import retrofit2.Response
import retrofit2.http.Query

interface MenuRemoteDataSource {
    suspend fun getCuisines(): Response<ResponseCuisines>
    suspend fun getSubCuisines(cuisines: String): Response<ResponseSubCategory>
    suspend fun getMenu(category: String, subCategory: String, page: Int, limit: Int): Response<ResponseMenu>
}