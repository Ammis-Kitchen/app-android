package com.ammiskitchen.ammiskitchenapp.data.firebase.services

import android.util.Log
import com.ammiskitchen.ammiskitchenapp.data.firebase.Response
import com.ammiskitchen.ammiskitchenapp.data.firebase.entities.FirebaseUserEntity
import com.google.firebase.auth.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await

class FirebaseAuthService
constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
) {

    private val USER_COLLECTION: String = "users"

    private val userCollection = firebaseFirestore.collection(USER_COLLECTION)


    suspend fun signInWithPhoneNumber(credential: PhoneAuthCredential): Response<FirebaseUserEntity> =
        try {
            val result = firebaseAuth.signInWithCredential(credential).await()
            val firebaseUserEntity = FirebaseUserEntity(
                id = result.user?.uid,
                phoneNumber = result.user?.phoneNumber
            )
            Response.Success(firebaseUserEntity)
        } catch (exception: Exception) {
            Response.Error(exception)
        }


    suspend fun checkIfUserExists(uid: String): Response<FirebaseUserEntity?> =
        try {
            val user = userCollection.document(uid).get().await()
            if(user.exists()) {
                Response.Success(user.toObject(FirebaseUserEntity::class.java))
            }
            else {
                Response.Success(null)
            }

        } catch (exception: Exception) {
            Response.Error(exception)
        }

//    suspend fun register(email: String, password: String): Response<AuthResult?> =
//        try {
//            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
//            Response.Success(result)
//        }
//        catch (exception: Exception) {
//            Response.Error(exception)
//        }
//
//
//    suspend fun login(email: String, password: String): Response<AuthResult?> =
//        try {
//            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
//            Response.Success(result)
//        }
//        catch (exception: Exception) {
//            Response.Error(exception)
//        }

    suspend fun createUserInFireStore(
        firebaseUserEntityMap: Map<String, Any>
    ): Response<Unit> =
        try {
            Log.d("createuserfirestore", firebaseUserEntityMap.getValue("id").toString());
            userCollection.document(firebaseUserEntityMap.getValue("id").toString())
                .set(firebaseUserEntityMap).await()
            Log.d("CREATE", "SUCCESS")
            Response.Success(Unit)

        } catch (exception: Exception) {
            Response.Error(exception)
        }

    suspend fun updateUserInFirestore(
        firebaseUserEntityMap: Map<String, Any>
    ): Response<Unit> =
        try {
            val id = firebaseUserEntityMap.getValue("id").toString()
            userCollection.document(id)
                .set(firebaseUserEntityMap.minus("id"), SetOptions.merge()).await()
            Response.Success(Unit)
        } catch (exception: Exception) {
            Response.Error(exception)
        }

    fun logout() = firebaseAuth.signOut()

    fun getCurrentUser() = firebaseAuth.currentUser

//    fun getCurrentUserListener(): MutableLiveData<FirebaseUserEntity?> {
//        val user = MutableLiveData<FirebaseUserEntity?>()
//
//        firebaseAuth.addIdTokenListener(FirebaseAuth.IdTokenListener {
//            val currentUser = firebaseAuth.currentUser
//            if(currentUser != null) {
//                user.value = FirebaseUserEntity(
//                    id = currentUser.uid,
//                    phoneNumber = currentUser.phoneNumber
//                )
//            }
//            else {
//                user.value = null
//            }
//        })
//        return user
//    }
}