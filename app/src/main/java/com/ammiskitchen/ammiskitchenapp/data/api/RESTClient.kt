package com.ammiskitchen.ammiskitchenapp.data.api

import com.ammiskitchen.ammiskitchenapp.BuildConfig
import com.ammiskitchen.ammiskitchenapp.data.api.service.AuthService
import com.ammiskitchen.ammiskitchenapp.data.api.service.MenuService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RESTClient {
    val okHttpBuilder = OkHttpClient.Builder()
        .readTimeout(30, TimeUnit.SECONDS)
        .connectTimeout(5, TimeUnit.SECONDS)

    val retrofitBuilder = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())

    val authService: AuthService = retrofitBuilder
        .client(okHttpBuilder.build())
        .build()
        .create(AuthService::class.java)

    val menuService: MenuService = retrofitBuilder
        .client(okHttpBuilder.build())
        .build()
        .create(MenuService::class.java)
}