package com.ammiskitchen.ammiskitchenapp.data.firebase.mappers

import com.ammiskitchen.ammiskitchenapp.data.firebase.entities.FirebaseMenuCategoryEntity
import com.ammiskitchen.ammiskitchenapp.domain.model.MenuCategory
import com.ammiskitchen.ammiskitchenapp.util.EntityMapper

class FirebaseMenuCategoryEntityMapper: EntityMapper<FirebaseMenuCategoryEntity, MenuCategory> {

    override fun mapFromEntityModel(entityModel: FirebaseMenuCategoryEntity): MenuCategory {
        return MenuCategory(entityModel.categories)
    }

    override fun mapToEntityModel(domainModel: MenuCategory): FirebaseMenuCategoryEntity {
        return FirebaseMenuCategoryEntity(domainModel.categories)
    }

}