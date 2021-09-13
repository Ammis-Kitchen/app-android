package com.ammiskitchen.ammiskitchenapp.domain.repository

import androidx.lifecycle.LiveData
import com.ammiskitchen.ammiskitchenapp.domain.model.CartItem
import com.ammiskitchen.ammiskitchenapp.util.DataState
import kotlinx.coroutines.flow.Flow

interface CartRepository {

    suspend fun insert(cartItem: CartItem)

    suspend fun update(cartItem: CartItem)

    suspend fun delete(cartItem: CartItem)

    fun getCart(): Flow<DataState<List<CartItem>>>

    suspend fun getItemsByCategory(category: String): DataState<List<CartItem>>

    fun getTotalItems(): LiveData<Double>

}