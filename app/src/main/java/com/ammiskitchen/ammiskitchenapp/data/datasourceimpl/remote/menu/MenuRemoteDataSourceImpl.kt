package com.ammiskitchen.ammiskitchenapp.data.datasourceimpl.remote.menu

import com.ammiskitchen.ammiskitchenapp.data.api.service.MenuService
import com.ammiskitchen.ammiskitchenapp.data.datasource.remote.menu.MenuRemoteDataSource
import com.ammiskitchen.ammiskitchenapp.data.models.response.ResponseCuisines
import com.ammiskitchen.ammiskitchenapp.data.models.response.ResponseMenu
import com.ammiskitchen.ammiskitchenapp.data.models.response.ResponseSubCategory
import retrofit2.Response

class MenuRemoteDataSourceImpl(private val menuService: MenuService): MenuRemoteDataSource {
    override suspend fun getCuisines(): Response<ResponseCuisines> {
        return menuService.getCuisines()
    }

    override suspend fun getSubCuisines(cuisines: String): Response<ResponseSubCategory> {
        return menuService.getSubCuisines(cuisines)
    }

    override suspend fun getMenu(
        category: String,
        subCategory: String,
        page: Int,
        limit: Int
    ): Response<ResponseMenu> {
        return menuService.getMenu(
            category, subCategory, page, limit
        )
    }
}