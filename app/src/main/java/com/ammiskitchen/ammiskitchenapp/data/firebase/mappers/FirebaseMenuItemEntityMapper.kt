package com.ammiskitchen.ammiskitchenapp.data.firebase.mappers

import com.ammiskitchen.ammiskitchenapp.data.firebase.entities.FirebaseMenuItemEntity
import com.ammiskitchen.ammiskitchenapp.domain.model.MenuItem
import com.ammiskitchen.ammiskitchenapp.util.EntityMapper

class FirebaseMenuItemEntityMapper: EntityMapper<FirebaseMenuItemEntity, MenuItem> {

    override fun mapFromEntityModel(entityModel: FirebaseMenuItemEntity): MenuItem {
        return MenuItem(
            itemId = entityModel.itemId!!,
            name = entityModel.name!!,
            category = entityModel.category!!,
            imageUrl = entityModel.imageUrl!!,
            description = entityModel.description!!,
            amount = entityModel.amount!!,
            quantity = entityModel.quantity!!
        )
    }

    override fun mapToEntityModel(domainModel: MenuItem): FirebaseMenuItemEntity {
        return FirebaseMenuItemEntity(
            itemId = domainModel.itemId,
            name = domainModel.name,
            category = domainModel.category,
            imageUrl = domainModel.imageUrl,
            description = domainModel.description,
            amount = domainModel.amount,
            quantity = domainModel.quantity
        )
    }

    fun mapFromEntityList(entities: List<FirebaseMenuItemEntity>): List<MenuItem> {
        return entities.map { mapFromEntityModel(it) }
    }

    fun mapToEntityList(domains: List<MenuItem>): List<FirebaseMenuItemEntity> {
        return domains.map { mapToEntityModel(it) }
    }
}