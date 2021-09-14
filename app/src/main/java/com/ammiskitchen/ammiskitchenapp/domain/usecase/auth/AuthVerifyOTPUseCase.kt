package com.ammiskitchen.ammiskitchenapp.domain.usecase.auth

import com.ammiskitchen.ammiskitchenapp.data.models.request.VerifyOTPRequest
import com.ammiskitchen.ammiskitchenapp.data.models.response.ResponseOTP
import com.ammiskitchen.ammiskitchenapp.data.util.Resource
import com.ammiskitchen.ammiskitchenapp.domain.repository.AmmisKitchenRepository

class AuthVerifyOTPUseCase(private val ammisKitchenRepository: AmmisKitchenRepository) {
    suspend fun execute(verifyOTPUseCase: VerifyOTPRequest): Resource<ResponseOTP> {
        return ammisKitchenRepository.authVerifyOTP(verifyOTPUseCase)
    }
}