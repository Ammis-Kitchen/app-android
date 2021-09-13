package com.ammiskitchen.ammiskitchenapp.domain.model

data class User(
    val id: String? = null,
    val name: UserName? = null,
    val address: List<String>? = null,
    val phoneNumber: String? = null,
    val email: String? = null
)