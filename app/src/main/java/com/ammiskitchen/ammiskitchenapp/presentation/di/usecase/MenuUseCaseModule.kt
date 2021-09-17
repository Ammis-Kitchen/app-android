package com.ammiskitchen.ammiskitchenapp.presentation.di.usecase

import com.ammiskitchen.ammiskitchenapp.domain.repository.AmmisKitchenRepository
import com.ammiskitchen.ammiskitchenapp.domain.usecase.menu.GetCuisinesUseCase
import com.ammiskitchen.ammiskitchenapp.domain.usecase.menu.GetMenuUseCase
import com.ammiskitchen.ammiskitchenapp.domain.usecase.menu.GetSubCuisinesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MenuUseCaseModule {
    @Provides
    @Singleton
    fun provideGetCuisinesUseCase(ammisKitchenRepository: AmmisKitchenRepository): GetCuisinesUseCase {
        return GetCuisinesUseCase(ammisKitchenRepository)
    }

    @Provides
    @Singleton
    fun provideGetSubCuisinesUseCase(ammisKitchenRepository: AmmisKitchenRepository): GetSubCuisinesUseCase {
        return GetSubCuisinesUseCase(ammisKitchenRepository)
    }

    @Provides
    @Singleton
    fun provideGetMenuUseCase(ammisKitchenRepository: AmmisKitchenRepository): GetMenuUseCase {
        return GetMenuUseCase(ammisKitchenRepository)
    }
}