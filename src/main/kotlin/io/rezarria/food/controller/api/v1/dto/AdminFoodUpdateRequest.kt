package io.rezarria.food.controller.api.v1.dto

data class AdminFoodUpdateRequest(
    val name: String,
    val description: String,
    val price: Double,
    val primaryImage: String,
    val categoryId: String?
)
