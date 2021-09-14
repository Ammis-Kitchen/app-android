package com.ammiskitchen.ammiskitchenapp.data.models.request


import com.google.gson.annotations.SerializedName

data class VerifyOTPRequest(
    @SerializedName("otp")
    val otp: String,
    @SerializedName("phoneNumber")
    val phoneNumber: String
)