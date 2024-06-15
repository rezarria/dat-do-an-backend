package io.rezarria.food.service

import io.rezarria.food.data.Food
import io.rezarria.food.repositories.FoodRepository
import org.bson.types.ObjectId
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class FoodService : BaseDataService<Food, FoodRepository>() {
    fun add(
        name: String,
        price: Double,
        description: String,
        primaryImage: String?,
        categoryId: ObjectId?
    ): Mono<Food> {
        return repository.save(Food(name).apply {
            this.price = price
            this.description = description
            this.primaryImage = primaryImage
            this.categoryId = categoryId
        })
    }
}