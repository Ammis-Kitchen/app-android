package com.ammiskitchen.ammiskitchenapp.domain.repository

import com.ammiskitchen.ammiskitchenapp.data.models.request.GetOTPRequest
import com.ammiskitchen.ammiskitchenapp.data.models.request.VerifyOTPRequest
import com.ammiskitchen.ammiskitchenapp.data.models.response.ResponseOTP
import com.ammiskitchen.ammiskitchenapp.data.util.Resource

interface AmmisKitchenRepository {
    suspend fun authGetOTP(getOTPRequest: GetOTPRequest): Resource<ResponseOTP>
    suspend fun authVerifyOTP(verifyOTPRequest: VerifyOTPRequest): Resource<ResponseOTP>
}