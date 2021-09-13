package com.ammiskitchen.ammiskitchenapp.domain.usecases

import com.ammiskitchen.ammiskitchenapp.domain.model.CartItem
import com.ammiskitchen.ammiskitchenapp.domain.model.Menu
import com.ammiskitchen.ammiskitchenapp.domain.repository.CartRepository
import com.ammiskitchen.ammiskitchenapp.domain.repository.MenuRepository
import com.ammiskitchen.ammiskitchenapp.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class GetMenuByCategoryWithCartUseCase(
    private val menuRepository: MenuRepository,
    private val cartRepository: CartRepository
) {

    suspend fun execute(category: String): Flow<DataState<Menu>> =
        menuRepository.getMenuByCategory(category)
            .map {
                it
                val cartItems = cartRepository.getItemsByCategory(category)
            }

}