package com.ammiskitchen.ammiskitchenapp.domain.usecase.auth

import com.ammiskitchen.ammiskitchenapp.data.models.request.GetOTPRequest
import com.ammiskitchen.ammiskitchenapp.data.models.response.ResponseOTP
import com.ammiskitchen.ammiskitchenapp.data.util.Resource
import com.ammiskitchen.ammiskitchenapp.domain.repository.AmmisKitchenRepository

class AuthGetOTPUseCase(private val ammisKitchenRepository: AmmisKitchenRepository) {
    suspend fun execute(getOTPRequest: GetOTPRequest):Resource<ResponseOTP> {
        return ammisKitchenRepository.authGetOTP(getOTPRequest)
    }
}