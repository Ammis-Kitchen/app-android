package com.ammiskitchen.ammiskitchenapp.data.models.entities


import com.google.gson.annotations.SerializedName

data class MenuList(
    @SerializedName("amount")
    val amount: Double,
    @SerializedName("category")
    val category: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("imageUrl")
    val imageUrl: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("quantity")
    val quantity: Double,
    @SerializedName("subCategory")
    val subCategory: String
)