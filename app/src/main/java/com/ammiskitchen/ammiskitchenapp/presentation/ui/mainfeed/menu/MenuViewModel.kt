package com.ammiskitchen.ammiskitchenapp.presentation.ui.mainfeed.menu

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.ammiskitchen.ammiskitchenapp.domain.usecase.menu.GetCuisinesUseCase
import com.ammiskitchen.ammiskitchenapp.domain.usecase.menu.GetMenuUseCase
import com.ammiskitchen.ammiskitchenapp.domain.usecase.menu.GetSubCuisinesUseCase

class MenuViewModel(
    private val app: Application,
    private val getCuisinesUseCase: GetCuisinesUseCase,
    private val getMenuUseCase: GetMenuUseCase,
    private val getSubCuisinesUseCase: GetSubCuisinesUseCase
) : AndroidViewModel(app) {
    // TODO: Implement the ViewModel
}