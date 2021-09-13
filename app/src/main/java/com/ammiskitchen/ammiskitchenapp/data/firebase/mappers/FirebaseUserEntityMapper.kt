package com.ammiskitchen.ammiskitchenapp.data.firebase.mappers

import com.ammiskitchen.ammiskitchenapp.data.firebase.entities.FirebaseUserEntity
import com.ammiskitchen.ammiskitchenapp.data.firebase.entities.FirebaseUserNameEntity
import com.ammiskitchen.ammiskitchenapp.domain.model.User
import com.ammiskitchen.ammiskitchenapp.domain.model.UserName
import com.ammiskitchen.ammiskitchenapp.util.EntityMapper
import com.google.firebase.firestore.FieldValue

class FirebaseUserEntityMapper: EntityMapper<FirebaseUserEntity, User> {

    override fun mapFromEntityModel(entityModel: FirebaseUserEntity): User {
        return User(
            id = entityModel.id,
            name = UserName(entityModel.name?.firstName, entityModel.name?.lastName),
            address = entityModel.address,
            phoneNumber = entityModel.phoneNumber,
            email = entityModel.email
        )
    }

    override fun mapToEntityModel(domainModel: User): FirebaseUserEntity {

        return FirebaseUserEntity(
            id = domainModel.id,
            name = FirebaseUserNameEntity(domainModel.name?.firstName, domainModel.name?.lastName),
            address = domainModel.address,
            phoneNumber = domainModel.phoneNumber,
            email = domainModel.email
        )
    }

    fun getEntityModelMap(firebaseUserEntity: FirebaseUserEntity): Map<String, Any> {

        val entityModelMap = mutableMapOf<String, Any>()

        val name = mutableMapOf<String, String>()
        firebaseUserEntity.name?.firstName?.let { name.put("firstName", it) }
        firebaseUserEntity.name?.lastName?.let { name.put("lastName", it) }
        if(name.isNotEmpty()) {
            entityModelMap.put("name", name)
        }

        firebaseUserEntity.id?.let { entityModelMap.put("id", it) }
        firebaseUserEntity.address?.let { entityModelMap.put("address", FieldValue.arrayUnion(it[0])) }
        firebaseUserEntity.phoneNumber?.let { entityModelMap.put("phone", it) }
        firebaseUserEntity.email?.let { entityModelMap.put("email", it) }

        return entityModelMap

    }
}