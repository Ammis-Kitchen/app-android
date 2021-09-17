package com.ammiskitchen.ammiskitchenapp.domain.usecase.menu

import com.ammiskitchen.ammiskitchenapp.data.models.response.ResponseMenu
import com.ammiskitchen.ammiskitchenapp.data.models.response.ResponseSubCategory
import com.ammiskitchen.ammiskitchenapp.data.util.Resource
import com.ammiskitchen.ammiskitchenapp.domain.repository.AmmisKitchenRepository

class GetSubCuisinesUseCase(private val ammisKitchenRepository: AmmisKitchenRepository) {
    suspend fun execute(cuisine: String): Resource<ResponseSubCategory> {
        return ammisKitchenRepository.getSubCuisines(cuisine)
    }
}