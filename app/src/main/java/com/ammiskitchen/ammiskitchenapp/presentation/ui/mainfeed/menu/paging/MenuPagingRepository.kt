package com.ammiskitchen.ammiskitchenapp.presentation.ui.mainfeed.menu.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ammiskitchen.ammiskitchenapp.data.models.entities.MenuList
import com.ammiskitchen.ammiskitchenapp.domain.usecase.menu.GetMenuUseCase
import kotlinx.coroutines.flow.Flow

class MenuPagingRepository(
    private val getMenuUseCase: GetMenuUseCase,
    private val cuisine: String,
    private val subCuisine: String
) {

    fun getMenuList(): Flow<PagingData<MenuList>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = true
            ),
            pagingSourceFactory = { MenuPagingDataSource(getMenuUseCase, cuisine, subCuisine) }
        ).flow
    }

}