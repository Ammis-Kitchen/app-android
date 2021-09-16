package com.ammiskitchen.ammiskitchenapp.data.models.response


import com.google.gson.annotations.SerializedName

data class ResponseSubCategory(
    @SerializedName("code")
    val code: Int,
    @SerializedName("result")
    val subcategory: List<String>,
    @SerializedName("status")
    val status: String
)