package io.rezarria.food.controller.api.v1.dto

import org.bson.types.ObjectId

data class AdminFoodRequest(
    val name: String,
    val primaryImage: String,
    val description: String,
    val price: Double,
    val categoryId: ObjectId?
)
