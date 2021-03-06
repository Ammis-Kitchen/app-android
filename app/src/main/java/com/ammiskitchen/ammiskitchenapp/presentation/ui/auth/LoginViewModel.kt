package com.ammiskitchen.ammiskitchenapp.presentation.ui.auth

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.ammiskitchen.ammiskitchenapp.domain.usecase.auth.AuthGetOTPUseCase
import com.ammiskitchen.ammiskitchenapp.domain.usecase.auth.AuthVerifyOTPUseCase

class LoginViewModel(
    private val app: Application,
    private val authGetOTPUseCase: AuthGetOTPUseCase,
    private val authVerifyOTPUseCase: AuthVerifyOTPUseCase
) : AndroidViewModel(app) {
    // TODO: Implement the ViewModel
}