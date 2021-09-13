package com.ammiskitchen.ammiskitchenapp.data.repository

import com.ammiskitchen.ammiskitchenapp.data.firebase.services.FirebaseAuthService
import com.ammiskitchen.ammiskitchenapp.data.firebase.mappers.FirebaseUserEntityMapper
import com.ammiskitchen.ammiskitchenapp.data.firebase.entities.FirebaseUserEntity
import com.ammiskitchen.ammiskitchenapp.data.firebase.Response
import com.ammiskitchen.ammiskitchenapp.domain.model.User
import com.ammiskitchen.ammiskitchenapp.domain.repository.UserRepository
import com.ammiskitchen.ammiskitchenapp.util.DataState
import com.google.firebase.auth.PhoneAuthCredential
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class UserRepositoryImpl
constructor(
    private val firebaseAuthService: FirebaseAuthService,
    private val firebaseUserEntityMapper: FirebaseUserEntityMapper
): UserRepository {

    override suspend fun signInWithPhoneNumber(credential: PhoneAuthCredential): Flow<DataState<User>> =
        flow {
            emit(DataState.Loading)
            when(val response = firebaseAuthService.signInWithPhoneNumber(credential)) {
                is Response.Success -> {
                    val user = firebaseUserEntityMapper.mapFromEntityModel(response.data!!)
                    emit(DataState.Success(user))
                }
                is Response.Error -> {
                    emit(DataState.Error(response.error!!, response.error.message))
                }
            }

        }.flowOn(Dispatchers.IO)

    override suspend fun checkIfUserExists(uid: String): Flow<DataState<User?>> = flow {
        emit(DataState.Loading)
        when(val response = firebaseAuthService.checkIfUserExists(uid)) {
            is Response.Success -> {
                if(response.data != null) {
                    val user = firebaseUserEntityMapper.mapFromEntityModel(response.data)
                    emit(DataState.Success(user))
                }
                else {
                    emit(DataState.Success(null))
                }
            }
            is Response.Error -> {
                emit(DataState.Error(response.error!!, response.error.message))
            }
        }
    }.flowOn((Dispatchers.IO))

    override suspend fun createUser(user: User): Flow<DataState<Unit>> = flow {
        emit(DataState.Loading)
        val firebaseUserEntity = firebaseUserEntityMapper.mapToEntityModel(user)
        val firebaseUserEntityMap = firebaseUserEntityMapper.getEntityModelMap(firebaseUserEntity)
        val response = firebaseAuthService.createUserInFireStore(firebaseUserEntityMap)

        when(response) {
            is Response.Success -> {
                emit(DataState.Success(response.data!!))
            }
            is Response.Error -> {
                emit(DataState.Error(response.error!!, response.error.message))
            }
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun updateUser(user: User): Flow<DataState<Unit>> = flow {
        emit(DataState.Loading)
        val firebaseUserEntity = firebaseUserEntityMapper.mapToEntityModel(user)
        val firebaseUserEntityMap = firebaseUserEntityMapper.getEntityModelMap(firebaseUserEntity)

        when(val response = firebaseAuthService.updateUserInFirestore(firebaseUserEntityMap)) {
            is Response.Success -> {
                emit(DataState.Success(response.data!!))
            }
            is Response.Error -> {
                emit(DataState.Error(response.error!!, response.error.message))
            }
        }
    }.flowOn(Dispatchers.IO)

    override fun getCurrentUser(): User? {
        val firebaseUser = firebaseAuthService.getCurrentUser()
        if(firebaseUser != null) {
            val firebaseUserEntity = FirebaseUserEntity(
                id = firebaseUser?.uid,
                phoneNumber = firebaseUser?.phoneNumber
            )
            val user = firebaseUserEntityMapper.mapFromEntityModel(firebaseUserEntity)
            return user
        }
        else {
            return null
        }
    }


}

//class UserRepositoryImpl
//@Inject
//constructor(
//    private val firebaseAuthService: FirebaseAuthService,
//    private val firebaseUserEntityMapper: FirebaseUserEntityMapper
//): UserRepository {
//
////    suspend fun register(email: String, password: String) =
////        FirebaseAuthService.register(email, password)
//
//    override suspend fun signInWithPhoneNumber(credential: PhoneAuthCredential) =
//        firebaseAuthService.signInWithPhoneNumber(credential)
//
//    override suspend fun createUserInFirestore(user: User): Response<Unit> {
//        val firebaseUserEntity: FirebaseUserEntity = firebaseUserEntityMapper.mapToEntityModel(user)
//        val firebaseUserEntityMap = firebaseUserEntityMapper.getEntityModelMap(firebaseUserEntity)
//        return firebaseAuthService.createUserInFireStore(firebaseUserEntityMap)
//    }
//
//
//
////    suspend fun login(email: String, password: String) =
////        FirebaseAuthService.login(email, password)
//
//    override suspend fun checkIfUserExists(uid: String) =
//        firebaseAuthService.checkIfUserExists(uid)
//
//    override suspend fun updateUserInFirestore(user: User): Response<Unit> {
//        val firebaseUserEntity: FirebaseUserEntity = firebaseUserEntityMapper.mapToEntityModel(user)
//        val firebaseUserEntityMap = firebaseUserEntityMapper.getEntityModelMap(firebaseUserEntity)
//        return firebaseAuthService.updateUserInFirestore(firebaseUserEntityMap)
//    }
//
//    override fun getCurrentUser() = firebaseAuthService.getCurrentUser()
//
//    override fun logout() = firebaseAuthService.logout()
//
//}