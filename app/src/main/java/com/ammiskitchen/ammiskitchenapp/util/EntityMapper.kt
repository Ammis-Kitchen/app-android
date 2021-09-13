package com.ammiskitchen.ammiskitchenapp.util

interface EntityMapper<EntityModel, DomainModel> {

    fun mapFromEntityModel(entityModel: EntityModel): DomainModel

    fun mapToEntityModel(domainModel: DomainModel): EntityModel

}