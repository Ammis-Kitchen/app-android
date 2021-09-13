package com.ammiskitchen.ammiskitchenapp.domain.usecases

import com.ammiskitchen.ammiskitchenapp.domain.repository.MenuRepository

class GetMenuByCategoryUseCase(
    private val menuRepository: MenuRepository
) {
    suspend fun execute(category: String) = menuRepository.getMenuByCategory(category)
}