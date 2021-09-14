package com.ammiskitchen.ammiskitchenapp.data.api.service

import com.ammiskitchen.ammiskitchenapp.data.models.request.GetOTPRequest
import com.ammiskitchen.ammiskitchenapp.data.models.request.VerifyOTPRequest
import com.ammiskitchen.ammiskitchenapp.data.models.response.ResponseOTP
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    //Phone Verification Get OTP
    @POST("auth/phone/verifications")
    suspend fun authGetOTP(@Body getOTPRequest: GetOTPRequest): Response<ResponseOTP>

    //Phone Verification Verify OTP
    @POST("auth/phone/verification-check")
    suspend fun authVerifyOTP(@Body verifyOTPRequest: VerifyOTPRequest): Response<ResponseOTP>
}