package com.ammiskitchen.ammiskitchenapp.di

import android.app.Application
import androidx.room.Room
import com.ammiskitchen.ammiskitchenapp.data.room.CartDatabase
import com.ammiskitchen.ammiskitchenapp.data.room.CartItemCacheEntityMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideCartDatabase(
        application: Application
    ) = Room
        .databaseBuilder(
            application,
            CartDatabase::class.java,
            "cart_database"
        )
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideCartDao(
        cartDatabase: CartDatabase
    ) = cartDatabase.getCartDao()

    @Singleton
    @Provides
    fun provideCartItemCachEntityMapper(): CartItemCacheEntityMapper {
        return CartItemCacheEntityMapper()
    }

}