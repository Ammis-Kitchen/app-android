package com.ammiskitchen.ammiskitchenapp.domain.model

class MenuItem(
    val itemId: String,
    val name: String,
    val category: String,
    val imageUrl: String,
    val description: String,
    val amount: Double,
    val quantity: Double,
    val inCart: Boolean = false,
    val totalQuantity: Double = 0.0,
    val totalAmount: Double = 0.0
)