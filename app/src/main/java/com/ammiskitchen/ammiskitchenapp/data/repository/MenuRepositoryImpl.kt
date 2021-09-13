package com.ammiskitchen.ammiskitchenapp.data.repository

import com.ammiskitchen.ammiskitchenapp.data.firebase.Response
import com.ammiskitchen.ammiskitchenapp.data.firebase.mappers.FirebaseMenuCategoryEntityMapper
import com.ammiskitchen.ammiskitchenapp.data.firebase.mappers.FirebaseMenuEntityMapper
import com.ammiskitchen.ammiskitchenapp.data.firebase.services.FirebaseMenuService
import com.ammiskitchen.ammiskitchenapp.domain.model.Menu
import com.ammiskitchen.ammiskitchenapp.domain.model.MenuCategory
import com.ammiskitchen.ammiskitchenapp.domain.repository.MenuRepository
import com.ammiskitchen.ammiskitchenapp.util.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MenuRepositoryImpl
constructor(
    private val firebaseMenuService: FirebaseMenuService,
    private val firebaseMenuCategoryEntityMapper: FirebaseMenuCategoryEntityMapper,
    private val firebaseMenuEntityMapper: FirebaseMenuEntityMapper
): MenuRepository {

    override suspend fun getMenuCategories(): Flow<DataState<MenuCategory>> = flow {
        emit(DataState.Loading)
        when(val response = firebaseMenuService.getMenuCategories()) {
            is Response.Success -> {
                val menuCategory: MenuCategory = firebaseMenuCategoryEntityMapper.mapFromEntityModel(response.data!!)
//                Log.d("FLOW_SUCCESS", menuCategory.toString())
                emit(DataState.Success(menuCategory))
            }
            is Response.Error -> {
                emit(DataState.Error(response.error!!, response.error.message))
            }
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getMenuByCategory(category: String): Flow<DataState<Menu>> = flow {
        emit(DataState.Loading)

        when(val response = firebaseMenuService.getMenuByCategory(category)) {
            is Response.Success -> {
                val menu = firebaseMenuEntityMapper.mapFromEntityModel(response.data!!)
                emit(DataState.Success(menu))
            }
            is Response.Error -> {
                emit(DataState.Error(response.error!!, response.error.message))
            }
        }

    }.flowOn(Dispatchers.IO)
}