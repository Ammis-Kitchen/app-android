package com.ammiskitchen.ammiskitchenapp.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ammiskitchen.ammiskitchenapp.domain.model.Menu
import com.ammiskitchen.ammiskitchenapp.domain.model.MenuCategory
import com.ammiskitchen.ammiskitchenapp.domain.model.MenuItem
import com.ammiskitchen.ammiskitchenapp.domain.repository.MenuRepository
import com.ammiskitchen.ammiskitchenapp.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel
@Inject
constructor(
    private val menuRepository: MenuRepository
) : ViewModel() {

    private val _menu: MutableMap<String, MutableLiveData<DataState<Menu>>> = mutableMapOf()
    val menu: Map<String, LiveData<DataState<Menu>>>
        get() = _menu

    private lateinit var _selectedMenuItem: MenuItem

    private val _menuCategoryState = MutableLiveData<DataState<MenuCategory>>()
    val menuCategoryState: LiveData<DataState<MenuCategory>>
        get() = _menuCategoryState


    private fun setSelectedMenuItem(recyclerViewPosition: Int, menuCategory: String) {
        val menu = _menu[menuCategory]?.value
       if(menu is DataState.Success) {
           _selectedMenuItem = menu.data.items[recyclerViewPosition]
       }
    }

    private fun getMenuByCategory(category: String) {
        _menu[category] = MutableLiveData()
        viewModelScope.launch {
            menuRepository.getMenuByCategory(category)
                .onEach { dataState ->
                    _menu[category]?.postValue(dataState)
//                            _menuState.postValue(dataState)
                }
                .launchIn(viewModelScope)
        }
    }

    private fun getMenuCategories() {
        viewModelScope.launch {
            menuRepository.getMenuCategories()
                .onEach { dataState ->
                    _menuCategoryState.postValue(dataState)
                }
                .launchIn(viewModelScope)
        }
    }


    fun getSelectedMenuItem() = _selectedMenuItem

    // also add the cart
    fun observeMenuCategory(category: String) = menu[category]

    fun setStateEvent(menuStateEvent: MenuStateEvent) {

        when (menuStateEvent) {
            is MenuStateEvent.GetMenuCategoryEvent -> {
                getMenuCategories()
            }
            is MenuStateEvent.GetMenuByCategoryEvent -> {
                getMenuByCategory(menuStateEvent.category)
            }
            is MenuStateEvent.SetSelectedMenuItemEvent -> {
                setSelectedMenuItem(
                    menuStateEvent.recyclerViewPosition,
                    menuStateEvent.menuCategory
                )
            }
            else -> return
        }

    }

}

sealed class MenuStateEvent {
    object GetMenuCategoryEvent : MenuStateEvent()
    data class GetMenuByCategoryEvent(val category: String) : MenuStateEvent()
    object None : MenuStateEvent()
    data class SetSelectedMenuItemEvent(
        val recyclerViewPosition: Int,
        val menuCategory: String
    ) : MenuStateEvent()
}