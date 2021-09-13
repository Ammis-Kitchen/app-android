package com.ammiskitchen.ammiskitchenapp.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.ammiskitchen.ammiskitchenapp.domain.model.User
import com.ammiskitchen.ammiskitchenapp.domain.repository.UserRepository
import com.ammiskitchen.ammiskitchenapp.util.DataState
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@HiltViewModel
class AuthViewModel
@Inject
constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    var countryCode: String? = null
    var phoneNumber: String? = null
    var verificationCode: String? = null
    var phoneAuthOptionsBuilder: PhoneAuthOptions.Builder? = null

    private var phoneAuthOptions: PhoneAuthOptions? = null

    private var verificationId: String? = null

    private var user: User? = null

    private val _currentUserState = MutableLiveData<DataState<User?>>()
    val currentUserState: LiveData<DataState<User?>>
        get() = _currentUserState

    private val _signInState = MutableLiveData<DataState<User>>()
    val signInState: LiveData<DataState<User>>
        get() = _signInState

    private val _userState = MutableLiveData<DataState<User?>>()
    val userState: LiveData<DataState<User?>>
        get() = _userState

    private val _OTPState = MutableLiveData<DataState<Unit>>()
    val OTPState: LiveData<DataState<Unit>>
        get() = _OTPState

    private val _createUserState = MutableLiveData<DataState<Unit>>()
    val createUserState: LiveData<DataState<Unit>>
        get() = _createUserState

    private val phoneAuthCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
            _OTPState.value = DataState.Success(Unit)
            signInWithPhoneAuthCredential(phoneAuthCredential)

        }

        override fun onVerificationFailed(exception: FirebaseException) {

            when(exception) {
                is FirebaseAuthInvalidCredentialsException -> {
                    _OTPState.value = DataState.Error(exception, "Enter a valid phone number.")
//                    _errorPhone.value = "Please enter a valid phone number."
//
//                    _phoneLayoutVisibility.value = true
//                    _logoVisibility.value = true
                }
                else -> {
                    _OTPState.value = DataState.Error(exception, "Something went Wrong. Please try again.")
//                    _phoneLayoutVisibility.value = true
//                    _logoVisibility.value = true
//                    _errorMessage.value = "Something went Wrong. Please try again."
                }
            }

            Log.d("TAG", "onVerificationFailed: ${exception.message.toString()}")
//            Toast.makeText(requireContext(), exception.message.toString(), Toast.LENGTH_LONG).show()

        }

        override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
            super.onCodeSent(verificationId, token)
            _OTPState.value = DataState.Success(Unit)
//            _verificationLayoutVisibility.value = true
//            _logoVisibility.value = true
            this@AuthViewModel.verificationId = verificationId

        }
    }

    private fun buildPhoneAuthOptionsBuilder() {
        val phoneNumber = "+$countryCode$phoneNumber"
        phoneAuthOptions = phoneAuthOptionsBuilder?.let {
            it.setPhoneNumber(phoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setCallbacks(phoneAuthCallbacks)
                .build()
        }
    }

    private fun signInWithPhoneAuthCredential(phoneAuthCredential: PhoneAuthCredential) {
        viewModelScope.launch {
            userRepository
                .signInWithPhoneNumber(phoneAuthCredential)
                .onEach { dataState ->

                    if(dataState is DataState.Success) {
                        user = dataState.data
                    }
                    _signInState.postValue(dataState)
                }
                .launchIn(viewModelScope)
        }
    }

    private fun checkIfUserAlreadyExists() {
        viewModelScope.launch {
            userRepository
                .checkIfUserExists(user?.id!!)
                .onEach { dataState ->
                    _userState.postValue(dataState)
                }
                .launchIn(viewModelScope)
        }

    }

    private fun createUser() {
        viewModelScope.launch {
            userRepository
                .createUser(user!!)
                .onEach { dataState ->
                    _createUserState.postValue(dataState)
                }
                .launchIn(viewModelScope)
        }
    }

    private fun getCurrentUser() {
        _currentUserState.value = DataState.Loading
        val user = userRepository
            .getCurrentUser()
        if(user != null) {
            _currentUserState.value = DataState.Success(user)
        }
        else {
            _currentUserState.value = DataState.Error(null, null)
        }
    }


    fun setStateEvent(authStateEvent: AuthStateEvent) {
        when (authStateEvent) {
            is AuthStateEvent.GetOTPEvent -> {
                _OTPState.value = DataState.Loading
                buildPhoneAuthOptionsBuilder()
                PhoneAuthProvider.verifyPhoneNumber(phoneAuthOptions!!)
            }
            is AuthStateEvent.SubmitOTPEvent -> {
                verificationId?.let {
                    val credential = PhoneAuthProvider.getCredential(it, verificationCode!!)
                    signInWithPhoneAuthCredential(credential)
                }
            }
            is AuthStateEvent.UserStateEvent -> {
                checkIfUserAlreadyExists()
            }
            is AuthStateEvent.CreateUserStateEvent -> {
                createUser()
            }
            is AuthStateEvent.GetUserEvent -> {
                getCurrentUser()
            }
            is AuthStateEvent.None -> {

            }
        }
    }

}

sealed class AuthStateEvent {
    object None : AuthStateEvent()
    object GetOTPEvent : AuthStateEvent()
    object SubmitOTPEvent : AuthStateEvent()
    object UserStateEvent: AuthStateEvent()
    object CreateUserStateEvent: AuthStateEvent()
    object GetUserEvent: AuthStateEvent()

}

////@HiltViewModel
//class AuthViewModel
////@Inject
//constructor(
//    private val userRepositoryImpl: UserRepository,
//    private val phoneAuthOptionsBuilder: PhoneAuthOptions.Builder
//): ViewModel() {
//
//    private val TAG = "AuthViewModel"
//
//    var phoneNumber: String? = null
//    var verificationCode: String? = null
////    var countryCode: String? = null
//    var countryCode: MutableLiveData<String> = MutableLiveData("+91")
//
//    private var verificationId: String? = null
//
//    private val _errorMessage: MutableLiveData<String?> = MutableLiveData(null)
//    val errorMessage: LiveData<String?>
//        get() = _errorMessage
//
//    private val _errorPhone: MutableLiveData<String?> = MutableLiveData(null)
//    val errorPhone: LiveData<String?>
//        get() = _errorPhone
//
//    private val _errorOTP: MutableLiveData<String?> = MutableLiveData(null)
//    val errorOTP: LiveData<String?>
//        get() = _errorOTP
//
//    private val _progressBarVisibility: MutableLiveData<Boolean> = MutableLiveData(false)
//    val progressBarVisibility: LiveData<Boolean>
//        get() = _progressBarVisibility
//
//    private val _goToUserDataFragment: MutableLiveData<Boolean> = MutableLiveData(false)
//    val goToUserDataFragment: LiveData<Boolean>
//        get() = _goToUserDataFragment
//
//    private val _goToHomeFragment: MutableLiveData<Boolean> = MutableLiveData(false)
//    val goToHomeFragment: LiveData<Boolean>
//        get() = _goToHomeFragment
//
//    private val _verificationLayoutVisibility: MutableLiveData<Boolean> = MutableLiveData(false)
//    val verificationLayoutVisibility: LiveData<Boolean>
//        get() = _verificationLayoutVisibility
//
//    private val _phoneLayoutVisibility: MutableLiveData<Boolean> = MutableLiveData(true)
//    val phoneLayoutVisibility: LiveData<Boolean>
//        get() = _phoneLayoutVisibility
//
//    private val _logoVisibility: MutableLiveData<Boolean> = MutableLiveData(true)
//    val logoVisibility: LiveData<Boolean>
//        get() = _logoVisibility
//
//    private val phoneAuthCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//        override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
//
//            signInWithPhoneAuthCredential(phoneAuthCredential)
//
//        }
//
//        override fun onVerificationFailed(exception: FirebaseException) {
//
//            when(exception) {
//                is FirebaseAuthInvalidCredentialsException -> {
//                    _errorPhone.value = "Please enter a valid phone number."
//                    _progressBarVisibility.value = false
//                    _phoneLayoutVisibility.value = true
//                    _logoVisibility.value = true
//                }
//                else -> {
//                    _progressBarVisibility.value = false
//                    _phoneLayoutVisibility.value = true
//                    _logoVisibility.value = true
//                    _errorMessage.value = "Something went Wrong. Please try again."
//                }
//            }
//
//            Log.d(TAG, "onVerificationFailed: ${exception.message.toString()}")
////            Toast.makeText(requireContext(), exception.message.toString(), Toast.LENGTH_LONG).show()
//
//        }
//
//        override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
//            super.onCodeSent(verificationId, token)
//            _progressBarVisibility.value = false
//            _verificationLayoutVisibility.value = true
//            _logoVisibility.value = true
//            this@AuthViewModel.verificationId = verificationId
//
//        }
//    }
//
//    // call when sendOTP is clicked
//    fun signInWithMobileNumber() {
//
////        Log.d(TAG, "signInWithMobileNumber: ${countryCode.value}")
//
//        _progressBarVisibility.value = true
//
//        val phone = "${countryCode.value}$phoneNumber"
//        val phoneAuthOptions = phoneAuthOptionsBuilder.setPhoneNumber(phone)
//            .setCallbacks(phoneAuthCallbacks)
//            .build()
//
//        PhoneAuthProvider.verifyPhoneNumber(phoneAuthOptions)
//        _phoneLayoutVisibility.value = false
//        _logoVisibility.value = false
//
//
//    }
//
//    // call when submit otp is clicked
//    fun submitVerificationCode() {
//        _progressBarVisibility.value = true
//        _verificationLayoutVisibility.value = false
//        _logoVisibility.value = false
//        verificationId?.let {
//            val credential = PhoneAuthProvider.getCredential(it, verificationCode!!)
//            signInWithPhoneAuthCredential(credential)
//        }
//
//    }
//
//    fun signInWithPhoneAuthCredential(phoneAuthCredential: PhoneAuthCredential) {
//        _progressBarVisibility.value= true
////        Log.d("DEBUG", "before coroutine")
//         viewModelScope.launch(Dispatchers.IO) {
////             Log.d("DEBUG", "before call")
//             val result = userRepositoryImpl.signInWithPhoneNumber(phoneAuthCredential)
//             Log.d("DEBUG", "after call")
//                 _progressBarVisibility.postValue(false)
//                 Log.d("DEBUG", "after call ${_progressBarVisibility.value.toString()}")
//
//             when(result) {
//                 is Response.Success -> {
//                     Log.d("RESPONSE.SUCCESS", result.data?.user.toString())
//
//                     val uid = result.data?.user?.uid
//
//                     val userDocument = userRepositoryImpl.checkIfUserExists(uid!!)
//                     Log.d("userDocument", userDocument.toString())
//                     when(userDocument) {
//                         is Response.Success -> {
//
//                             if(userDocument.data?.exists() == true) {
//                                 _goToHomeFragment.postValue(true)
//                             }
//                             else {
//
//                                 Log.d("create user", "creating user")
//                                 val phone = "${countryCode.value}$phoneNumber"
//                                 val user = User(id = uid, phoneNumber = phone)
////                                 _errorMessage.postValue("creatuserinfirestore call ho raha hai")
//                                 userRepositoryImpl.createUserInFirestore(user)
////                                 _errorMessage.postValue("creatuserinfirestore call hua")
//                                 Log.d("created user", "created user")
//                                 _goToUserDataFragment.postValue(true)
//                             }
//                         }
//                         is Response.Error -> {
//                             Log.d("RESPONSE.ERROR check", userDocument.error?.message.toString())
//                             _errorMessage.postValue("Something went wrong.")
//                         }
//                     }
//
//                 }
//                 is Response.Error -> {
////                     withContext(Dispatchers.Main) {
//                         Log.d("RESPONSE.ERROR", result.error?.message.toString())
////                     }
//                     _progressBarVisibility.postValue(false)
//                     _verificationLayoutVisibility.postValue(true)
//                     _logoVisibility.postValue(true)
//                     _errorOTP.postValue("Invalid OTP.")
//                 }
//             }
//         }
//
//    }
//
//
//
////    var email: String? = null
////    var password: String? = null
////    var name: String? = null
////
//
////
////    private val _errorPassword: MutableLiveData<String?> = MutableLiveData(null)
////    val errorPassword: LiveData<String?>
////        get() = _errorPassword
////
////    private val _errorEmail: MutableLiveData<String?> = MutableLiveData(null)
////    val errorEmail: LiveData<String?>
////        get() = _errorEmail
////
////    val user by lazy { userRepository.currentUser() }
////
////    fun isValidCredentials(): Boolean {
////        if(email.isNullOrEmpty() || password.isNullOrEmpty()) {
////            return false
////        }
////
////        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
////            return false
////        }
////
//////        if(password!!.length < 8) {
//////            return false
//////        }
////
////        return true
////    }
////
////    fun register() {
////
////        _progressBarVisibility.value = true
////
////        if(isValidCredentials()) {
////            viewModelScope.launch(Dispatchers.IO) {
//////                delay(5000L)
////                val result = userRepository.register(email!!, password!!)
////
////                when(result) {
////
////                    is Response.Success -> {
////                        userRepository.createUserInFirestore(result.data?.user?.uid,name!!)
////                    }
////                    is Response.Error -> {
////
////                        when(result.error) {
////                            is FirebaseAuthWeakPasswordException -> {
////                                withContext(Dispatchers.Main) {
////                                    _errorPassword.value = result.error.message.toString()
////                                }
////                            }
////                            else -> {
////                                withContext(Dispatchers.Main) {
////                                    _errorEmail.value = result.error?.message.toString()
////                                }
////                            }
////                        }
////
////                    }
////                }
////                withContext(Dispatchers.Main) {
////                    _progressBarVisibility.value = false
////                }
////            }
////        }
////
////    }
////
////    fun login() {
////
////        if(isValidCredentials()) {
////            viewModelScope.launch(Dispatchers.IO) {
////                userRepository.login(email!!, password!!)
////            }
////        }
////
////    }
//
//
//}