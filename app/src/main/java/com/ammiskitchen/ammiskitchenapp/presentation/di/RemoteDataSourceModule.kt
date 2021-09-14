package com.ammiskitchen.ammiskitchenapp.presentation.di

import com.ammiskitchen.ammiskitchenapp.data.api.service.AuthService
import com.ammiskitchen.ammiskitchenapp.data.datasource.remote.auth.AuthRemoteDataSource
import com.ammiskitchen.ammiskitchenapp.data.datasourceimpl.remote.auth.AuthRemoteDataSourceImpl
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
}