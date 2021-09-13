package com.ammiskitchen.ammiskitchenapp.domain.model

data class CartItem(
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