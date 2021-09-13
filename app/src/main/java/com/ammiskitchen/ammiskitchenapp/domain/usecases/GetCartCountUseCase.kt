package com.ammiskitchen.ammiskitchenapp.domain.usecases

import com.ammiskitchen.ammiskitchenapp.domain.repository.CartRepository

class GetCartCountUseCase(
    private val cartRepository: CartRepository
) {
    fun execute() = cartRepository.getTotalItems()
}