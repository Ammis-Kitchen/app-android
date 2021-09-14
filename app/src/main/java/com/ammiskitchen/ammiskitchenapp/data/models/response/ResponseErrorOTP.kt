package com.ammiskitchen.ammiskitchenapp.data.models.response

import com.ammiskitchen.ammiskitchenapp.data.models.entities.Result
import com.google.gson.annotations.SerializedName

data class ResponseErrorOTP(
    @SerializedName("code")
    val code: Int,
    @SerializedName("details")
    val details: String,
    @SerializedName("status")
    val status: String,
)
