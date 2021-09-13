package com.ammiskitchen.ammiskitchenapp.data.firebase.services

import android.util.Log
import com.ammiskitchen.ammiskitchenapp.data.firebase.Response
import com.ammiskitchen.ammiskitchenapp.data.firebase.entities.FirebaseMenuCategoryEntity
import com.ammiskitchen.ammiskitchenapp.data.firebase.entities.FirebaseMenuEntity
import com.ammiskitchen.ammiskitchenapp.data.firebase.entities.FirebaseMenuItemEntity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirebaseMenuService
constructor(
    private val firebaseFirestore: FirebaseFirestore
) {

    companion object {
        private const val MENU_COLLECTION: String = "menu"
        private const val UTILS_COLLECTION: String = "utils"
    }


    private val menuCollection = firebaseFirestore.collection(MENU_COLLECTION)
    private val utilCollection = firebaseFirestore.collection(UTILS_COLLECTION)

    suspend fun getMenuCategories(): Response<FirebaseMenuCategoryEntity?> = try {
        val categories = utilCollection.document("menu")
            .get().await()
            .toObject(FirebaseMenuCategoryEntity::class.java)
//        Log.d("SERVICE_CATEGORY", categories.toString())
        Response.Success(categories)
    } catch (exception: Exception) {
//        Log.d("SERVICE_CATEGORY_ERROR", exception.message.toString())
        Response.Error(exception)
    }

    suspend fun getMenuByCategory(category: String): Response<FirebaseMenuEntity> =
        try {
            val menuItems = menuCollection
                .whereEqualTo("category", category)
                .get().await()
                .toObjects(FirebaseMenuItemEntity::class.java)
            val menu = FirebaseMenuEntity(menuItems)
            Response.Success(menu)
        } catch (exception: Exception) {
            Log.d("serial", exception.message.toString())
            Response.Error(exception)
        }

}