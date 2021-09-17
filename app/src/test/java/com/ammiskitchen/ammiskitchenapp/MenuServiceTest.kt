package com.ammiskitchen.ammiskitchenapp

import com.ammiskitchen.ammiskitchenapp.data.api.RESTClient
import com.ammiskitchen.ammiskitchenapp.data.models.response.ResponseErrorOTP
import com.google.gson.Gson
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MenuServiceTest {
    @Test
    fun `GET cuisines when successfull returns status OK`() {
        runBlocking {
            val response = RESTClient.menuService.getCuisines()
            assertEquals("OK", response.body()?.status)
        }
    }

    @Test
    fun `GET sub-cuisines provided correct cuisines returns status OK`() {
        runBlocking {
            val response = RESTClient.menuService.getSubCuisines("chinese")
            assertEquals("OK", response.body()?.status)
        }
    }

    @Test
    fun `GET sub-cuisines provided incorrect cuisines returns status NOT_FOUND`() {
        runBlocking {
            val response = RESTClient.menuService.getSubCuisines("chines")
            val errorResponse: ResponseErrorOTP = Gson().fromJson(response.errorBody()!!.charStream(), ResponseErrorOTP::class.java)
            assertEquals("NOT_FOUND", errorResponse.status)
        }
    }

    @Test
    fun `GET menu provided correct category, sub-category, page, limit returns status OK`() {
        runBlocking {
            val response = RESTClient.menuService.getMenu(
                category = "italian",
                subCategory = "main",
                page = 1,
                limit = 10
            )
            assertEquals("OK", response.body()?.status)
        }
    }

    @Test
    fun `GET menu provided incorrect category returns status NOT_FOUND`() {
        runBlocking {
            val response = RESTClient.menuService.getMenu(
                category = "italia",
                subCategory = "main",
                page = 1,
                limit = 10
            )
            val errorResponse: ResponseErrorOTP = Gson().fromJson(response.errorBody()!!.charStream(), ResponseErrorOTP::class.java)
            assertEquals("NOT_FOUND", errorResponse.status)
        }
    }

    @Test
    fun `GET menu provided incorrect sub-category returns status NOT_FOUND`() {
        runBlocking {
            val response = RESTClient.menuService.getMenu(
                category = "italian",
                subCategory = "mai",
                page = 1,
                limit = 10
            )
            val errorResponse: ResponseErrorOTP = Gson().fromJson(response.errorBody()!!.charStream(), ResponseErrorOTP::class.java)
            assertEquals("NOT_FOUND", errorResponse.status)
        }
    }

    @Test
    fun `GET menu provided invalid Page returns status NOT_FOUND`() {
        runBlocking {
            val response = RESTClient.menuService.getMenu(
                category = "italian",
                subCategory = "main",
                page = 100,
                limit = 10
            )
            val errorResponse: ResponseErrorOTP = Gson().fromJson(response.errorBody()!!.charStream(), ResponseErrorOTP::class.java)
            assertEquals("NOT_FOUND", errorResponse.status)
        }
    }

    @Test
    fun `GET menu provided invalid limit returns status 500`() {
        runBlocking {
            val response = RESTClient.menuService.getMenu(
                category = "italian",
                subCategory = "main",
                page = 1,
                limit = 0
            )
            val errorResponse: ResponseErrorOTP = Gson().fromJson(response.errorBody()!!.charStream(), ResponseErrorOTP::class.java)
            assertEquals("500", errorResponse.status)
        }
    }
    
}