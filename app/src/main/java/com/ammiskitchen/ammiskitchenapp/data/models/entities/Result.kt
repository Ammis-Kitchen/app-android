package com.ammiskitchen.ammiskitchenapp.data.models.entities


import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("exists")
    val exists: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("verificationStatus")
    val verificationStatus: String,
    @SerializedName("user")
    val user: User
)