package com.ammiskitchen.ammiskitchenapp.presentation.di.usecase

import com.ammiskitchen.ammiskitchenapp.domain.repository.AmmisKitchenRepository
import com.ammiskitchen.ammiskitchenapp.domain.usecase.auth.AuthGetOTPUseCase
import com.ammiskitchen.ammiskitchenapp.domain.usecase.auth.AuthVerifyOTPUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AuthUseCaseModule {
    @Provides
    @Singleton
    fun provideAuthGetOTPUseCase(ammisKitchenRepository: AmmisKitchenRepository): AuthGetOTPUseCase {
        return AuthGetOTPUseCase(ammisKitchenRepository)
    }

    @Provides
    @Singleton
    fun provideAuthVerifyOTPUseCase(ammisKitchenRepository: AmmisKitchenRepository): AuthVerifyOTPUseCase {
        return AuthVerifyOTPUseCase(ammisKitchenRepository)
    }
}