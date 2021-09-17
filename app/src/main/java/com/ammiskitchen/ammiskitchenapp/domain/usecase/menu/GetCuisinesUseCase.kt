package com.ammiskitchen.ammiskitchenapp.domain.usecase.menu

import com.ammiskitchen.ammiskitchenapp.data.models.response.ResponseCuisines
import com.ammiskitchen.ammiskitchenapp.data.util.Resource
import com.ammiskitchen.ammiskitchenapp.domain.repository.AmmisKitchenRepository

class GetCuisinesUseCase(private val ammisKitchenRepository: AmmisKitchenRepository) {
    suspend fun execute(): Resource<ResponseCuisines> {
        return ammisKitchenRepository.getCuisines()
    }
}