package com.ammiskitchen.ammiskitchenapp.data.datasourceimpl.remote.auth

import com.ammiskitchen.ammiskitchenapp.data.api.service.AuthService
import com.ammiskitchen.ammiskitchenapp.data.datasource.remote.auth.AuthRemoteDataSource
import com.ammiskitchen.ammiskitchenapp.data.models.request.GetOTPRequest
import com.ammiskitchen.ammiskitchenapp.data.models.request.VerifyOTPRequest
import com.ammiskitchen.ammiskitchenapp.data.models.response.ResponseOTP
import retrofit2.Response

class AuthRemoteDataSourceImpl(private val authService: AuthService): AuthRemoteDataSource {
    override suspend fun getOTP(getOTPRequest: GetOTPRequest): Response<ResponseOTP> {
        return authService.authGetOTP(getOTPRequest)
    }

    override suspend fun verifyOTP(verifyOTPRequest: VerifyOTPRequest): Response<ResponseOTP> {
        return authService.authVerifyOTP(verifyOTPRequest)
    }
}