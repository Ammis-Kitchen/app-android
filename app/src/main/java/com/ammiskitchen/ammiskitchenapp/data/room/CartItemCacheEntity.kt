package com.ammiskitchen.ammiskitchenapp.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "cart_table"
)
data class CartItemCacheEntity(
    @PrimaryKey(
        autoGenerate = true
    )
    val id: Int = 0,
    val name: String,
    val category: String,
    val imageUrl: String,
    val description: String,
    val amount: Double,
    val quantity: Double,
    val totalAmount: Double,
    val totalQuantity: Double
)