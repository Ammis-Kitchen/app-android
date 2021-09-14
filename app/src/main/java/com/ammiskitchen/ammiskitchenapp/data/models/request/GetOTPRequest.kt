package com.ammiskitchen.ammiskitchenapp.data.models.request


import com.google.gson.annotations.SerializedName

data class GetOTPRequest(
    @SerializedName("phoneNumber")
    val phoneNumber: String
)