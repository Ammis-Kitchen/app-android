package com.ammiskitchen.ammiskitchenapp.data.room

import com.ammiskitchen.ammiskitchenapp.domain.model.CartItem
import com.ammiskitchen.ammiskitchenapp.util.EntityMapper

class CartItemCacheEntityMapper: EntityMapper<CartItemCacheEntity, CartItem> {

    override fun mapFromEntityModel(entityModel: CartItemCacheEntity): CartItem =
        CartItem(
            id = entityModel.id,
            name = entityModel.name,
            category = entityModel.category,
            imageUrl = entityModel.imageUrl,
            description = entityModel.description,
            amount = entityModel.amount,
            quantity = entityModel.quantity,
            totalAmount = entityModel.totalAmount,
            totalQuantity = entityModel.totalQuantity
        )

    override fun mapToEntityModel(domainModel: CartItem): CartItemCacheEntity =
        CartItemCacheEntity(
            name = domainModel.name,
            category = domainModel.category,
            imageUrl = domainModel.imageUrl,
            description = domainModel.description,
            amount = domainModel.amount,
            quantity = domainModel.quantity,
            totalAmount = domainModel.totalAmount,
            totalQuantity = domainModel.totalQuantity
        )

    fun mapFromEntityList(entities: List<CartItemCacheEntity>): List<CartItem> {
        return entities.map { mapFromEntityModel(it) }
    }

}