package com.ammiskitchen.ammiskitchenapp.presentation.ui.mainfeed.menu

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ammiskitchen.ammiskitchenapp.domain.usecase.menu.GetCuisinesUseCase
import com.ammiskitchen.ammiskitchenapp.domain.usecase.menu.GetMenuUseCase
import com.ammiskitchen.ammiskitchenapp.domain.usecase.menu.GetSubCuisinesUseCase

class MenuViewModelFactory(
    private val app: Application,
    private val getCuisinesUseCase: GetCuisinesUseCase,
    private val getMenuUseCase: GetMenuUseCase,
    private val getSubCuisinesUseCase: GetSubCuisinesUseCase
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MenuViewModel(app, getCuisinesUseCase, getMenuUseCase, getSubCuisinesUseCase) as T
    }
}