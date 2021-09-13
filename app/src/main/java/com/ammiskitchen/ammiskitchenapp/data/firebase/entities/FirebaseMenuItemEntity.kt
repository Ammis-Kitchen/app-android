package com.ammiskitchen.ammiskitchenapp.data.firebase.entities

data class FirebaseMenuItemEntity(
    val itemId: String? = null,
    val name: String? = null,
    val category: String? = null,
    val imageUrl: String? = null,
    val description: String? = null,
    val amount: Double? = null,
    val quantity: Double? = null
)