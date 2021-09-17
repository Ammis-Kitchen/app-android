package com.ammiskitchen.ammiskitchenapp.presentation.di

import com.ammiskitchen.ammiskitchenapp.data.datasource.remote.auth.AuthRemoteDataSource
import com.ammiskitchen.ammiskitchenapp.data.datasource.remote.menu.MenuRemoteDataSource
import com.ammiskitchen.ammiskitchenapp.data.repositoryimpl.AmmisKitchenRepositoryImpl
import com.ammiskitchen.ammiskitchenapp.domain.repository.AmmisKitchenRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Singleton
    @Provides
    fun provideAmmisKitchenRepository(authRemoteDataSource: AuthRemoteDataSource, menuRemoteDataSource: MenuRemoteDataSource): AmmisKitchenRepository {
        return AmmisKitchenRepositoryImpl(authRemoteDataSource, menuRemoteDataSource)
    }
}