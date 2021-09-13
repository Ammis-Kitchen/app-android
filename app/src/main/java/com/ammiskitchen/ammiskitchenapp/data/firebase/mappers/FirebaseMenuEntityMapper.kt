package com.ammiskitchen.ammiskitchenapp.data.firebase.mappers

import com.ammiskitchen.ammiskitchenapp.data.firebase.entities.FirebaseMenuEntity
import com.ammiskitchen.ammiskitchenapp.domain.model.Menu
import com.ammiskitchen.ammiskitchenapp.util.EntityMapper
import javax.inject.Inject

class FirebaseMenuEntityMapper
@Inject
constructor(
    private val firebaseMenuItemEntityMapper: FirebaseMenuItemEntityMapper
): EntityMapper<FirebaseMenuEntity, Menu> {

    override fun mapFromEntityModel(entityModel: FirebaseMenuEntity): Menu {
        return Menu(
            items = firebaseMenuItemEntityMapper.mapFromEntityList(entityModel.items)
        )
    }

    override fun mapToEntityModel(domainModel: Menu): FirebaseMenuEntity {
        return FirebaseMenuEntity(
            items = firebaseMenuItemEntityMapper.mapToEntityList(domainModel.items)
        )
    }

}