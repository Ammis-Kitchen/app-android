package com.ammiskitchen.ammiskitchenapp

import com.ammiskitchen.ammiskitchenapp.data.api.RESTClient
import com.ammiskitchen.ammiskitchenapp.data.api.service.AuthService
import com.ammiskitchen.ammiskitchenapp.data.models.request.GetOTPRequest
import com.ammiskitchen.ammiskitchenapp.data.models.request.VerifyOTPRequest
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

class AuthServiceTest {

    @Test
    fun `OTP when correct details provided returns status OK`() {
        runBlocking {
            val getOTPRequest = GetOTPRequest("+919987746997")
            val response = RESTClient.authService.authGetOTP(getOTPRequest)
            assertEquals("OK", response.body()?.status)
        }
    }

    @Test
    fun `OTP when incorrect details provided returns status INTERNAL_SERVER_ERROR`() {
        runBlocking {
            val getOTPRequest = GetOTPRequest("+91998774699")
            val response = RESTClient.authService.authGetOTP(getOTPRequest)
            val errorResponse: ResponseErrorOTP = Gson().fromJson(response.errorBody()!!.charStream(), ResponseErrorOTP::class.java)
            assertEquals("INTERNAL_SERVER_ERROR", errorResponse.status)
        }
    }

    @Test
    fun `correct OTP incorrect number returns status INTERNAL_SERVER_ERROR`() {
        runBlocking {
            val verifyOTPRequest = VerifyOTPRequest("091117", "+91998774699")
            val response = RESTClient.authService.authVerifyOTP(verifyOTPRequest)
            val errorResponse: ResponseErrorOTP = Gson().fromJson(response.errorBody()!!.charStream(), ResponseErrorOTP::class.java)
            assertEquals("INTERNAL_SERVER_ERROR", errorResponse.status)
        }
    }

    @Test
    fun `incorrect OTP correct number returns status UNAUTHORIZED`() {
        runBlocking {
            val verifyOTPRequest = VerifyOTPRequest("02549", "+919987746997")
            val response = RESTClient.authService.authVerifyOTP(verifyOTPRequest)
            val errorResponse: ResponseErrorOTP = Gson().fromJson(response.errorBody()!!.charStream(), ResponseErrorOTP::class.java)
            assertEquals("UNAUTHORIZED", errorResponse.status)
        }
    }

    @Test
    fun `correct OTP correct number returns status OK`() {
        runBlocking {
            val verifyOTPRequest = VerifyOTPRequest("800572", "+919987746997")
            val response = RESTClient.authService.authVerifyOTP(verifyOTPRequest)
            assertEquals("OK", response.body()?.status)
        }
    }
}