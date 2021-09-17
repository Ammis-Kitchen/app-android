package com.ammiskitchen.ammiskitchenapp.presentation.di

import com.ammiskitchen.ammiskitchenapp.data.api.service.AuthService
import com.ammiskitchen.ammiskitchenapp.data.api.service.MenuService
import com.ammiskitchen.ammiskitchenapp.data.datasource.remote.auth.AuthRemoteDataSource
import com.ammiskitchen.ammiskitchenapp.data.datasource.remote.menu.MenuRemoteDataSource
import com.ammiskitchen.ammiskitchenapp.data.datasourceimpl.remote.auth.AuthRemoteDataSourceImpl
import com.ammiskitchen.ammiskitchenapp.data.datasourceimpl.remote.menu.MenuRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteDataSourceModule {
    @Singleton
    @Provides
    fun provideAuthRemoteDataSource(authService: AuthService): AuthRemoteDataSource {
        return AuthRemoteDataSourceImpl(authService)
    }

    @Singleton
    @Provides
    fun provideMenuRemoteDataSource(menuService: MenuService): MenuRemoteDataSource {
        return MenuRemoteDataSourceImpl(menuService)
    }
}