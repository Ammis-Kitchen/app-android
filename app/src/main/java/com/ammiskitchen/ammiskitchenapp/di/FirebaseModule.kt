package com.ammiskitchen.ammiskitchenapp.di

import com.ammiskitchen.ammiskitchenapp.data.firebase.mappers.FirebaseMenuCategoryEntityMapper
import com.ammiskitchen.ammiskitchenapp.data.firebase.mappers.FirebaseMenuEntityMapper
import com.ammiskitchen.ammiskitchenapp.data.firebase.mappers.FirebaseMenuItemEntityMapper
import com.ammiskitchen.ammiskitchenapp.data.firebase.services.FirebaseAuthService
import com.ammiskitchen.ammiskitchenapp.data.firebase.mappers.FirebaseUserEntityMapper
import com.ammiskitchen.ammiskitchenapp.data.firebase.services.FirebaseMenuService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Singleton
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Singleton
    @Provides
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Singleton
    @Provides
    fun provideFirebaseAuthService(
        firebaseAuth: FirebaseAuth,
        firebaseFirestore: FirebaseFirestore
    ): FirebaseAuthService {
        return FirebaseAuthService(firebaseAuth, firebaseFirestore)
    }

    @Singleton
    @Provides
    fun provideFirebaseUserEntityMapper(): FirebaseUserEntityMapper {
        return FirebaseUserEntityMapper()
    }

    @Singleton
    @Provides
    fun provideFirebaseMenuService(
        firebaseFirestore: FirebaseFirestore
    ): FirebaseMenuService {
        return FirebaseMenuService(firebaseFirestore)
    }

    @Singleton
    @Provides
    fun provideFirebaseMenuCategoryEntityMapper(): FirebaseMenuCategoryEntityMapper {
        return FirebaseMenuCategoryEntityMapper()
    }

    @Singleton
    @Provides
    fun provideFirebaseMenuEntityMapper(
        firebaseMenuItemEntityMapper: FirebaseMenuItemEntityMapper
    ): FirebaseMenuEntityMapper {
        return FirebaseMenuEntityMapper(firebaseMenuItemEntityMapper)
    }

    @Singleton
    @Provides
    fun provideFirebaseMenuItemEntityMapper(): FirebaseMenuItemEntityMapper {
        return FirebaseMenuItemEntityMapper()
    }

}