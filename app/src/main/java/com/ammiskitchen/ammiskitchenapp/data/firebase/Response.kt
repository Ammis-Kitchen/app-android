package com.ammiskitchen.ammiskitchenapp.data.firebase

import java.lang.Exception

sealed class Response<T>(
    val data: T? = null,
    val error: Exception? = null
) {

    class Success<T>(data: T): Response<T>(data)
//    class Loading<T>(data: T? = null): Response<T>(data)
    class Error<T>(exception: Exception): Response<T>(error = exception)

}