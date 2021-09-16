package com.ammiskitchen.ammiskitchenapp.data.models.response


import com.google.gson.annotations.SerializedName

data class ResponseCuisines(
    @SerializedName("code")
    val code: Int,
    @SerializedName("result")
    val cuisines: List<String>,
    @SerializedName("status")
    val status: String
)