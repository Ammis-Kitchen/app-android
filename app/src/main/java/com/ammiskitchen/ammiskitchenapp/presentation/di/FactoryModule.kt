package com.ammiskitchen.ammiskitchenapp.presentation.di

import android.app.Application
import com.ammiskitchen.ammiskitchenapp.domain.usecase.auth.AuthGetOTPUseCase
import com.ammiskitchen.ammiskitchenapp.domain.usecase.auth.AuthVerifyOTPUseCase
import com.ammiskitchen.ammiskitchenapp.domain.usecase.menu.GetCuisinesUseCase
import com.ammiskitchen.ammiskitchenapp.domain.usecase.menu.GetMenuUseCase
import com.ammiskitchen.ammiskitchenapp.domain.usecase.menu.GetSubCuisinesUseCase
import com.ammiskitchen.ammiskitchenapp.presentation.ui.auth.LoginViewModelFactory
import com.ammiskitchen.ammiskitchenapp.presentation.ui.mainfeed.menu.MenuViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FactoryModule {
    @Singleton
    @Provides
    fun provideLoginViewModelFactory(app: Application, authGetOTPUseCase: AuthGetOTPUseCase, authVerifyOTPUseCase: AuthVerifyOTPUseCase): LoginViewModelFactory {
        return LoginViewModelFactory(
            app, authGetOTPUseCase, authVerifyOTPUseCase
        )
    }

    @Singleton
    @Provides
    fun provideMenuViewModelFactory(app: Application, getCuisinesUseCase: GetCuisinesUseCase, getMenuUseCase: GetMenuUseCase, getSubCuisinesUseCase: GetSubCuisinesUseCase): MenuViewModelFactory {
        return MenuViewModelFactory(
            app, getCuisinesUseCase, getMenuUseCase, getSubCuisinesUseCase
        )
    }
}