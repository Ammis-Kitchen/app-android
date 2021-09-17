package com.ammiskitchen.ammiskitchenapp.data.repositoryimpl

import com.ammiskitchen.ammiskitchenapp.data.datasource.remote.auth.AuthRemoteDataSource
import com.ammiskitchen.ammiskitchenapp.data.datasource.remote.menu.MenuRemoteDataSource
import com.ammiskitchen.ammiskitchenapp.data.models.request.GetOTPRequest
import com.ammiskitchen.ammiskitchenapp.data.models.request.VerifyOTPRequest
import com.ammiskitchen.ammiskitchenapp.data.models.response.*
import com.ammiskitchen.ammiskitchenapp.data.util.Resource
import com.ammiskitchen.ammiskitchenapp.domain.repository.AmmisKitchenRepository
import com.google.gson.Gson

class AmmisKitchenRepositoryImpl(
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val menuRemoteDataSource: MenuRemoteDataSource
): AmmisKitchenRepository {
    override suspend fun authGetOTP(getOTPRequest: GetOTPRequest): Resource<ResponseOTP> {
        val response = authRemoteDataSource.getOTP(getOTPRequest)
        if (response.code() == 200) {
            response.body()?.let {
                return Resource.Success(data = it)
            }
        } else if (response.code() == 500) {
            return Resource.Error(message = "Internal server error. Please try again later.")
        }
        return Resource.Error(message = "Internal server error. Please try again later.")
    }

    override suspend fun authVerifyOTP(verifyOTPRequest: VerifyOTPRequest): Resource<ResponseOTP> {
        val response = authRemoteDataSource.verifyOTP(verifyOTPRequest)
        if (response.code() == 200) {
            response.body()?.let {
                return Resource.Success(data = it)
            }
        } else if (response.code() == 500) {
            return Resource.Error(message = "Internal server error. Please try again later.")
        } else if (response.code() == 401) {
            return Resource.Error(message = "Incorrect OTP")
        }
        return Resource.Error(message = "Internal server error. Please try again later.")
    }

    override suspend fun getCuisines(): Resource<ResponseCuisines> {
        val response = menuRemoteDataSource.getCuisines()
        if (response.code() == 200) {
            response.body()?.let {
                return Resource.Success(data = it)
            }
        }
        return Resource.Error(message = "Internal server error. Please try again later.")
    }

    override suspend fun getSubCuisines(cuisines: String): Resource<ResponseSubCategory> {
        val response = menuRemoteDataSource.getSubCuisines(cuisines)
        if (response.code() == 200) {
            response.body()?.let {
                return Resource.Success(data = it)
            }
        }
        return Resource.Error(message = "Internal server error. Please try again later.")
    }

    override suspend fun getMenu(
        category: String,
        subCategory: String,
        page: Int,
        limit: Int
    ): Resource<ResponseMenu> {
        val response = menuRemoteDataSource.getMenu(category, subCategory, page, limit)
        if (response.code() == 200) {
            response.body()?.let {
                return Resource.Success(data = it)
            }
        } else if (response.code() == 500) {
            return Resource.Error(message = "Internal server error. Please try again later.")
        }
        return Resource.Error(message = "No more cuisines available.")
    }
}