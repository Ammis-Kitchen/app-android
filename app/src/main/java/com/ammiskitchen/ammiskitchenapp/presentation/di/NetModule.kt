package com.ammiskitchen.ammiskitchenapp.presentation.di

import com.ammiskitchen.ammiskitchenapp.data.api.RESTClient
import com.ammiskitchen.ammiskitchenapp.data.api.service.AuthService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetModule {
    @Singleton
    @Provides
    fun provideAuthService(): AuthService {
        return RESTClient.authService
    }
}