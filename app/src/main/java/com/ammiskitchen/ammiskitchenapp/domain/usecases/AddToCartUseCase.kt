package com.ammiskitchen.ammiskitchenapp.domain.usecases

import com.ammiskitchen.ammiskitchenapp.domain.model.CartItem
import com.ammiskitchen.ammiskitchenapp.domain.repository.CartRepository

class AddToCartUseCase(
    private val cartRepository: CartRepository
) {
    suspend fun execute(cartItem: CartItem) = cartRepository.insert(cartItem)
}