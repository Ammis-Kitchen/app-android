package com.ammiskitchen.ammiskitchenapp.presentation.di

import com.ammiskitchen.ammiskitchenapp.presentation.ui.mainfeed.menu.adapter.CuisinesListAdapter
import com.ammiskitchen.ammiskitchenapp.presentation.ui.mainfeed.menu.adapter.MenuListAdapter
import com.ammiskitchen.ammiskitchenapp.presentation.ui.mainfeed.menu.adapter.MenuLoadStateAdapter
import com.ammiskitchen.ammiskitchenapp.presentation.ui.mainfeed.menu.adapter.SubCuisinesListAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AdapterModule {
    @Singleton
    @Provides
    fun provideCuisinesListAdapter(): CuisinesListAdapter {
        return CuisinesListAdapter()
    }

    @Singleton
    @Provides
    fun provideSubCuisinesListAdapter(): SubCuisinesListAdapter {
        return SubCuisinesListAdapter()
    }

    @Singleton
    @Provides
    fun provideMenuListAdapter(): MenuListAdapter {
        return MenuListAdapter()
    }

    @Singleton
    @Provides
    fun provideMenuLoadStateAdapter(): MenuLoadStateAdapter {
        return MenuLoadStateAdapter()
    }
}