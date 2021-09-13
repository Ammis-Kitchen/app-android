package com.ammiskitchen.ammiskitchenapp.presentation.viewmodels

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ammiskitchen.ammiskitchenapp.domain.model.User
import com.ammiskitchen.ammiskitchenapp.domain.model.UserName
import com.ammiskitchen.ammiskitchenapp.domain.repository.UserRepository
import com.ammiskitchen.ammiskitchenapp.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDataViewModel
@Inject
constructor(
    private val userRepository: UserRepository
): ViewModel() {

    var firstName: String? = null
    var lastName: String? = null
    var email: String? = null
    var address: String? = null

    private var user: User? = null

    private val _errorFirstName: MutableLiveData<String?> = MutableLiveData()
    val errorFirstName: LiveData<String?>
        get() = _errorFirstName

    private val _errorLastName: MutableLiveData<String?> = MutableLiveData()
    val errorLastName: LiveData<String?>
        get() = _errorLastName

    private val _errorEmail: MutableLiveData<String?> = MutableLiveData()
    val errorEmail: LiveData<String?>
        get() = _errorEmail

    private val _errorAddress: MutableLiveData<String?> = MutableLiveData()
    val errorAddress: LiveData<String?>
        get() = _errorAddress

    private val _saveUserDataState = MutableLiveData<DataState<Unit>>()
    val saveUserDataState: LiveData<DataState<Unit>>
        get() = _saveUserDataState

    fun setStateEvent(userDataStateEvent: UserDataStateEvent) {
        when(userDataStateEvent) {
            is UserDataStateEvent.SaveUserDataStateEvent -> {
                getCurrentUser()
                saveUserData()
            }
            is UserDataStateEvent.None -> {

            }
        }
    }

    private fun validateUserData(): Boolean {
        var verified = true
        if(firstName.isNullOrEmpty()) {
            verified = false
            _errorFirstName.value = "Enter a valid first name."
        }
        if(lastName.isNullOrEmpty()) {
            verified = false
            _errorLastName.value = "Enter a valid last name."
        }
        if(email.isNullOrEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email!!).matches()) {
            verified = false
            _errorEmail.value = "Enter a valid email id."
        }
        if(address.isNullOrEmpty()) {
            verified = false
            _errorAddress.value = "Enter a valid Address."
        }
        return verified
    }

    private fun getCurrentUser() {
        user = userRepository.getCurrentUser()
    }

    private fun saveUserData() {
        if(user != null) {
            if(validateUserData()) {
                val saveUser = User(
                    id = user!!.id,
                    name = UserName(
                        firstName = firstName,
                        lastName = lastName
                    ),
                    email = email,
                    address = listOf(address!!)
                )
                viewModelScope.launch {
                    userRepository
                        .updateUser(saveUser)
                        .onEach { dataState ->
                            _saveUserDataState.postValue(dataState)
                        }
                        .launchIn(viewModelScope)
                }
            }

        }
        else {
            _saveUserDataState.value = DataState.Error(UserNotFoundException(), "No User Found.")
        }

    }
//
//    private val _errorMessage: MutableLiveData<String?> = MutableLiveData(null)
//    val errorMessage: LiveData<String?>
//        get() = _errorMessage
//
//    private val _errorFirstName: MutableLiveData<String?> = MutableLiveData(null)
//    val errorFirstName: LiveData<String?>
//        get() = _errorFirstName
//
//    private val _errorLastName: MutableLiveData<String?> = MutableLiveData(null)
//    val errorLastName: LiveData<String?>
//        get() = _errorLastName
//
//    private val _errorEmail: MutableLiveData<String?> = MutableLiveData(null)
//    val errorEmail: LiveData<String?>
//        get() = _errorEmail
//
//    private val _errorAddress: MutableLiveData<String?> = MutableLiveData(null)
//    val errorAddress: LiveData<String?>
//        get() = _errorAddress
//
//    private val _progressBarVisibility: MutableLiveData<Boolean> = MutableLiveData(false)
//    val progressBarVisibility: LiveData<Boolean>
//        get() = _progressBarVisibility
//
//    private val _goToHomeFragment: MutableLiveData<Boolean> = MutableLiveData(false)
//    val goToHomeFragment: LiveData<Boolean>
//        get() = _goToHomeFragment
//
//
//    fun onSkip() {
//        _goToHomeFragment.value = true
//    }
//
//    fun onContinueClick() {
//        _errorFirstName.value = ""
//        _errorEmail.value = ""
//        _errorLastName.value = ""
//        _progressBarVisibility.value = true
//        var verified = true
//
//        if(firstName.isNullOrEmpty()) {
//            verified = false
//            _errorFirstName.value = "Enter a valid first name."
//        }
//        if(lastName.isNullOrEmpty()) {
//            verified = false
//            _errorLastName.value = "Enter a valid last name."
//        }
//        if(email.isNullOrEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email!!).matches()) {
//            verified = false
//            _errorEmail.value = "Enter a valid email id."
//        }
//        if(address.isNullOrEmpty()) {
//            verified = false
//            _errorAddress.value = "Enter a valid Address."
//        }
//
//        if(verified) {
//            val uid = userRepositoryImpl.getCurrentUser()?.uid
////            val phoneNumber = userRepositoryImpl.getCurrentUser()?.phoneNumber
//
//            viewModelScope.launch(Dispatchers.IO) {
//                val user = User(
//                    id = uid!!,
//                    name = UserName(firstName!!, lastName!!),
//                    email = email,
//                    address = listOf(address!!)
//                )
//                val result = userRepositoryImpl.updateUserInFirestore(user)
//                _progressBarVisibility.postValue(false)
//                when(result) {
//                    is Response.Success -> {
//                        _goToHomeFragment.postValue(true)
//                    }
//                    is Response.Error -> {
//                        _errorMessage.postValue(result.error?.message.toString())
////                        Log.e("Response.Error", result.error?.message.toString())
//                    }
//                }
//
//            }
//
//        }
//        else {
//            _progressBarVisibility.value = false
//
//            return
//        }
//
//    }

}

sealed class UserDataStateEvent {
    object SaveUserDataStateEvent: UserDataStateEvent()
    object None: UserDataStateEvent()
}

class UserNotFoundException: Exception()