package com.ammiskitchen.ammiskitchenapp.data.models.entities


import com.google.gson.annotations.SerializedName

data class MenuResult(
    @SerializedName("data")
    val `data`: List<MenuList>,
    @SerializedName("page")
    val page: Page
)