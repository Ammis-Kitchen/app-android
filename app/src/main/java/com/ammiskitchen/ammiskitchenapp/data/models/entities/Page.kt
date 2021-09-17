package com.ammiskitchen.ammiskitchenapp.data.models.entities


import com.google.gson.annotations.SerializedName

data class Page(
    @SerializedName("current")
    val current: Int,
    @SerializedName("next")
    val next: Int,
    @SerializedName("previous")
    val previous: Int,
    @SerializedName("totalRecords")
    val totalRecords: Int
)