package com.ammiskitchen.ammiskitchenapp.domain.usecase.menu

import com.ammiskitchen.ammiskitchenapp.data.models.response.ResponseCuisines
import com.ammiskitchen.ammiskitchenapp.data.models.response.ResponseMenu
import com.ammiskitchen.ammiskitchenapp.data.util.Resource
import com.ammiskitchen.ammiskitchenapp.domain.repository.AmmisKitchenRepository

class GetMenuUseCase(private val ammisKitchenRepository: AmmisKitchenRepository) {
    suspend fun execute(category: String, subCategory: String, page: Int, limit: Int): Resource<ResponseMenu> {
        return ammisKitchenRepository.getMenu(category, subCategory, page, limit)
    }
}