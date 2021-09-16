package com.ammiskitchen.ammiskitchenapp.data.api.service

import com.ammiskitchen.ammiskitchenapp.data.models.response.ResponseCuisines
import com.ammiskitchen.ammiskitchenapp.data.models.response.ResponseSubCategory
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MenuService {
    //GET Cuisines
    @GET("menu/cuisines")
    suspend fun getCuisines(): Response<ResponseCuisines>

    //GET  Sub-cuisines
    @GET("menu/cuisines/{cuisines}/sub-categories")
    suspend fun getSubCuisines(
        @Path("cuisines") cuisines: String
    ): Response<ResponseSubCategory>
}