package com.ammiskitchen.ammiskitchenapp.data.datasource.remote.auth

import com.ammiskitchen.ammiskitchenapp.data.models.request.GetOTPRequest
import com.ammiskitchen.ammiskitchenapp.data.models.request.VerifyOTPRequest
import com.ammiskitchen.ammiskitchenapp.data.models.response.ResponseOTP
import retrofit2.Response

interface AuthRemoteDataSource {
    suspend fun getOTP(getOTPRequest: GetOTPRequest): Response<ResponseOTP>
    suspend fun verifyOTP(verifyOTPRequest: VerifyOTPRequest): Response<ResponseOTP>
}