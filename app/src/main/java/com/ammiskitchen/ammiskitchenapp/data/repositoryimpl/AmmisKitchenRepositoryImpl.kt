package com.ammiskitchen.ammiskitchenapp.data.repositoryimpl

import com.ammiskitchen.ammiskitchenapp.data.datasource.remote.auth.AuthRemoteDataSource
import com.ammiskitchen.ammiskitchenapp.data.models.request.GetOTPRequest
import com.ammiskitchen.ammiskitchenapp.data.models.request.VerifyOTPRequest
import com.ammiskitchen.ammiskitchenapp.data.models.response.ResponseErrorOTP
import com.ammiskitchen.ammiskitchenapp.data.models.response.ResponseOTP
import com.ammiskitchen.ammiskitchenapp.data.util.Resource
import com.ammiskitchen.ammiskitchenapp.domain.repository.AmmisKitchenRepository
import com.google.gson.Gson

class AmmisKitchenRepositoryImpl(
    private val authRemoteDataSource: AuthRemoteDataSource
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
}