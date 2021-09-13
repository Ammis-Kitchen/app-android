package com.ammiskitchen.ammiskitchenapp.di

import com.ammiskitchen.ammiskitchenapp.domain.repository.CartRepository
import com.ammiskitchen.ammiskitchenapp.domain.usecases.AddToCartUseCase
import com.ammiskitchen.ammiskitchenapp.domain.usecases.GetCartCountUseCase
import com.ammiskitchen.ammiskitchenapp.domain.usecases.GetCartUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Singleton
    @Provides
    fun provideAddToCartUseCase(
        cartRepository: CartRepository
    ): AddToCartUseCase {
        return AddToCartUseCase(
            cartRepository
        )
    }

    @Singleton
    @Provides
    fun provideGetCartCountUseCase(
        cartRepository: CartRepository
    ): GetCartCountUseCase {
        return GetCartCountUseCase(cartRepository)
    }

    @Singleton
    @Provides
    fun provideGetCartUseCase(
        cartRepository: CartRepository
    ): GetCartUseCase {
        return GetCartUseCase(cartRepository)
    }

}