package com.ammiskitchen.ammiskitchenapp.data.models.response


import com.ammiskitchen.ammiskitchenapp.data.models.entities.MenuResult
import com.google.gson.annotations.SerializedName

data class ResponseMenu(
    @SerializedName("code")
    val code: Int,
    @SerializedName("result")
    val menuResult: MenuResult,
    @SerializedName("status")
    val status: String
)