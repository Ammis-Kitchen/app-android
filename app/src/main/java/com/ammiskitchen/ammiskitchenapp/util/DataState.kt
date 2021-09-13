package com.ammiskitchen.ammiskitchenapp.util

sealed class DataState<out R> {
    data class Success<out T>(val data: T) : DataState<T>()
    data class Error(val exception: Exception?, val message: String?) : DataState<Nothing>()
    object Loading : DataState<Nothing>()
}