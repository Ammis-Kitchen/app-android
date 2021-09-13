package com.ammiskitchen.ammiskitchenapp.domain.repository

import com.ammiskitchen.ammiskitchenapp.domain.model.User
import com.ammiskitchen.ammiskitchenapp.util.DataState
import com.google.firebase.auth.PhoneAuthCredential
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun signInWithPhoneNumber(credential: PhoneAuthCredential): Flow<DataState<User>>

    suspend fun checkIfUserExists(uid: String): Flow<DataState<User?>>

    suspend fun createUser(user: User): Flow<DataState<Unit>>

    suspend fun updateUser(user: User): Flow<DataState<Unit>>

    fun getCurrentUser(): User?

}

//interface UserRepository {
//
//    suspend fun signInWithPhoneNumber(credential: PhoneAuthCredential): Response<AuthResult>
//
//    suspend fun createUserInFirestore(user: User): Response<Unit>
//
//    suspend fun checkIfUserExists(uid: String): Response<DocumentSnapshot>
//
//    suspend fun updateUserInFirestore(user: User): Response<Unit>
//
//    fun getCurrentUser(): FirebaseUser?
//
//    fun logout()
//
//}