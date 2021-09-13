package com.ammiskitchen.ammiskitchenapp.di

import com.ammiskitchen.ammiskitchenapp.data.firebase.mappers.FirebaseMenuCategoryEntityMapper
import com.ammiskitchen.ammiskitchenapp.data.firebase.mappers.FirebaseMenuEntityMapper
import com.ammiskitchen.ammiskitchenapp.data.firebase.services.FirebaseAuthService
import com.ammiskitchen.ammiskitchenapp.data.firebase.mappers.FirebaseUserEntityMapper
import com.ammiskitchen.ammiskitchenapp.data.firebase.services.FirebaseMenuService
import com.ammiskitchen.ammiskitchenapp.domain.repository.CartRepository
import com.ammiskitchen.ammiskitchenapp.domain.repository.MenuRepository
import com.ammiskitchen.ammiskitchenapp.domain.repository.UserRepository
import com.ammiskitchen.ammiskitchenapp.data.repository.CartRepositoryImpl
import com.ammiskitchen.ammiskitchenapp.data.repository.MenuRepositoryImpl
import com.ammiskitchen.ammiskitchenapp.data.repository.UserRepositoryImpl
import com.ammiskitchen.ammiskitchenapp.data.room.CartDao
import com.ammiskitchen.ammiskitchenapp.data.room.CartItemCacheEntityMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideUserRepository(
        firebaseUserEntityMapper: FirebaseUserEntityMapper,
        firebaseAuthService: FirebaseAuthService
    ): UserRepository {
        return UserRepositoryImpl(
            firebaseAuthService,
            firebaseUserEntityMapper
        )
    }

    @Singleton
    @Provides
    fun provideMenuRepository(
        firebaseMenuService: FirebaseMenuService,
        firebaseMenuCategoryEntityMapper: FirebaseMenuCategoryEntityMapper,
        firebaseMenuEntityMapper: FirebaseMenuEntityMapper
    ): MenuRepository {
        return MenuRepositoryImpl(
            firebaseMenuService,
            firebaseMenuCategoryEntityMapper,
            firebaseMenuEntityMapper
        )
    }

    @Singleton
    @Provides
    fun provideCartRepository(
        cartDao: CartDao,
        cartItemCacheEntityMapper: CartItemCacheEntityMapper
    ): CartRepository {
        return CartRepositoryImpl(
            cartDao,
            cartItemCacheEntityMapper
        )
    }
}