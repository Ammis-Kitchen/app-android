package com.ammiskitchen.ammiskitchenapp.domain.repository

import com.ammiskitchen.ammiskitchenapp.domain.model.Menu
import com.ammiskitchen.ammiskitchenapp.domain.model.MenuCategory
import com.ammiskitchen.ammiskitchenapp.util.DataState
import kotlinx.coroutines.flow.Flow

interface MenuRepository {

    suspend fun getMenuCategories(): Flow<DataState<MenuCategory>>

    suspend fun getMenuByCategory(category: String): Flow<DataState<Menu>>

}