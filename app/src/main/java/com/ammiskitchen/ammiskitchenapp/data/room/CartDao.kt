package com.ammiskitchen.ammiskitchenapp.data.room

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {

    @Insert(
        onConflict = OnConflictStrategy.REPLACE
    )
    suspend fun insert(cartItemCacheEntity: CartItemCacheEntity)

    @Update
    suspend fun update(cartItemCacheEntity: CartItemCacheEntity)

    @Delete
    suspend fun delete(cartItemCacheEntity: CartItemCacheEntity)

    @Query("SELECT SUM(totalQuantity) as totalItems FROM cart_table")
    fun getTotalItems(): LiveData<Double>

    @Query("SELECT * FROM cart_table")
    fun getAll(): Flow<List<CartItemCacheEntity>>

    @Query("SELECT * FROM cart_table WHERE category = :category")
    suspend fun getItemsByCategory(category: String): List<CartItemCacheEntity>

    @Query("SELECT * FROM cart_table where id = :itemId")
    suspend fun checkIfExists(itemId: Int): CartItemCacheEntity

}