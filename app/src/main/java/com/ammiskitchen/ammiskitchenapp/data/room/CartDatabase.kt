package com.ammiskitchen.ammiskitchenapp.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [CartItemCacheEntity::class],
    version = 1
)
abstract class CartDatabase: RoomDatabase() {

    abstract fun getCartDao(): CartDao

}