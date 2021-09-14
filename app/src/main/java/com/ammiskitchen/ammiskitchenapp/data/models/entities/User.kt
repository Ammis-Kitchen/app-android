package com.ammiskitchen.ammiskitchenapp.data.models.entities

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id")
    val id: String,
    @SerializedName("phoneNumber")
    val phoneNumber: String
)
