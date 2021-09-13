package com.ammiskitchen.ammiskitchenapp.data.firebase.entities

data class FirebaseUserEntity(
    val id: String? = null,
    val name: FirebaseUserNameEntity? = null,
    val address: List<String>? = null,
    val phoneNumber: String? = null,
    val email: String? = null
)