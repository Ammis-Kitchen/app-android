package com.ammiskitchen.ammiskitchenapp.presentation.ui.mainfeed.menu.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ammiskitchen.ammiskitchenapp.data.exception.NotFoundException
import com.ammiskitchen.ammiskitchenapp.data.models.entities.MenuList
import com.ammiskitchen.ammiskitchenapp.data.util.Resource
import com.ammiskitchen.ammiskitchenapp.domain.usecase.menu.GetMenuUseCase
import java.lang.Exception

class MenuPagingDataSource(
    private val getMenuUseCase: GetMenuUseCase,
    private val cuisine: String,
    private val subCuisine: String
): PagingSource<Int, MenuList>() {

    private var START_PAGE: Int = 1

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MenuList> {
        val position = params.key ?: START_PAGE

        return try {
            val response = getMenuUseCase.execute(
                cuisine, subCuisine, position, params.loadSize
            )
            when(response) {
                is Resource.Success -> {
                    response.data?.let {
                        val nextKey = if (it.menuResult.data.isEmpty()) {
                            null
                        } else {
                            position + 1
                        }
                        LoadResult.Page(
                            data = it.menuResult.data,
                            prevKey = if (position == START_PAGE) null else position - 20,
                            nextKey = nextKey
                        )
                    }!!
                }
                is Resource.Error -> {
                    throw NotFoundException("No more cuisines available.")
                }
                is Resource.Loading -> throw NotFoundException("No more cuisines available.")
            }
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    @ExperimentalPagingApi
    override fun getRefreshKey(state: PagingState<Int, MenuList>): Int? {
        return super.getRefreshKey(state)
    }
}