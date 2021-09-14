package com.ammiskitchen.ammiskitchenapp.data.models.response


import com.ammiskitchen.ammiskitchenapp.data.models.entities.Result
import com.google.gson.annotations.SerializedName

data class ResponseOTP(
    @SerializedName("code")
    val code: Int,
    @SerializedName("result")
    val result: Result,
    @SerializedName("status")
    val status: String,
    @SerializedName("details")
    val details: String
)