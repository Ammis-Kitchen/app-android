package com.ammiskitchen.ammiskitchenapp.presentation.ui.auth

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ammiskitchen.ammiskitchenapp.domain.usecase.auth.AuthGetOTPUseCase
import com.ammiskitchen.ammiskitchenapp.domain.usecase.auth.AuthVerifyOTPUseCase

class LoginViewModelFactory(
    private val app: Application,
    private val authGetOTPUseCase: AuthGetOTPUseCase,
    private val authVerifyOTPUseCase: AuthVerifyOTPUseCase
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginViewModel(
            app, authGetOTPUseCase, authVerifyOTPUseCase
        ) as T
    }
}