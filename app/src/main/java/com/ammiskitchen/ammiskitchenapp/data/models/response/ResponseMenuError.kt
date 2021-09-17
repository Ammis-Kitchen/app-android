package com.ammiskitchen.ammiskitchenapp.data.models.response


import com.google.gson.annotations.SerializedName

data class ResponseMenuError(
    @SerializedName("error")
    val error: String,
    @SerializedName("path")
    val path: String,
    @SerializedName("status")
    val status: Int,
    @SerializedName("timestamp")
    val timestamp: String
)