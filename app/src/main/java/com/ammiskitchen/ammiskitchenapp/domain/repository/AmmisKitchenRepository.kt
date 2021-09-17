package com.ammiskitchen.ammiskitchenapp.domain.repository

import com.ammiskitchen.ammiskitchenapp.data.models.request.GetOTPRequest
import com.ammiskitchen.ammiskitchenapp.data.models.request.VerifyOTPRequest
import com.ammiskitchen.ammiskitchenapp.data.models.response.ResponseCuisines
import com.ammiskitchen.ammiskitchenapp.data.models.response.ResponseMenu
import com.ammiskitchen.ammiskitchenapp.data.models.response.ResponseOTP
import com.ammiskitchen.ammiskitchenapp.data.models.response.ResponseSubCategory
import com.ammiskitchen.ammiskitchenapp.data.util.Resource

interface AmmisKitchenRepository {
    suspend fun authGetOTP(getOTPRequest: GetOTPRequest): Resource<ResponseOTP>
    suspend fun authVerifyOTP(verifyOTPRequest: VerifyOTPRequest): Resource<ResponseOTP>

    //Menu
    suspend fun getCuisines(): Resource<ResponseCuisines>
    suspend fun getSubCuisines(cuisines: String): Resource<ResponseSubCategory>
    suspend fun getMenu(
        category: String,
        subCategory: String,
        page: Int,
        limit: Int
    ): Resource<ResponseMenu>
}