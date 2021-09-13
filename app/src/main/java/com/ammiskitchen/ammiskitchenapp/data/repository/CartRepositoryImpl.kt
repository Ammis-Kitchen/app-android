package com.ammiskitchen.ammiskitchenapp.data.repository

import androidx.lifecycle.LiveData
import com.ammiskitchen.ammiskitchenapp.domain.model.CartItem
import com.ammiskitchen.ammiskitchenapp.domain.repository.CartRepository
import com.ammiskitchen.ammiskitchenapp.data.room.CartDao
import com.ammiskitchen.ammiskitchenapp.data.room.CartItemCacheEntityMapper
import com.ammiskitchen.ammiskitchenapp.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class CartRepositoryImpl
constructor(
    private val cartDao: CartDao,
    private val mapper: CartItemCacheEntityMapper
) : CartRepository {
    override suspend fun insert(cartItem: CartItem) {
        val cartItemCacheEntity = mapper.mapToEntityModel(cartItem)
        cartDao.insert(cartItemCacheEntity)
    }

    override suspend fun update(cartItem: CartItem) {
        val cartItemCacheEntity = mapper.mapToEntityModel(cartItem)
        cartDao.update(cartItemCacheEntity)
    }

    override suspend fun delete(cartItem: CartItem) {
        val cartItemCacheEntity = mapper.mapToEntityModel(cartItem)
        cartDao.delete(cartItemCacheEntity)
    }

    override fun getCart(): Flow<DataState<List<CartItem>>> =
        cartDao.getAll()
            .catch { }
            .map {
                DataState.Success(mapper.mapFromEntityList(it))
            }

    override suspend fun getItemsByCategory(category: String): DataState<List<CartItem>> {
        val cartItemCatchEntities = cartDao.getItemsByCategory(category)
        val cartItems = mapper.mapFromEntityList(cartItemCatchEntities)
        return DataState.Success(cartItems)
    }

    override fun getTotalItems(): LiveData<Double> = cartDao.getTotalItems()


}