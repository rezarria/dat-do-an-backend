package io.rezarria.food.controller.api.v1

import io.rezarria.food.controller.api.v1.dto.AdminFoodRequest
import io.rezarria.food.data.Food
import io.rezarria.food.repositories.FoodRepository
import io.rezarria.food.service.FoodService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/v1/food")
class FoodController : BaseController<FoodService, Food, FoodRepository>() {

    @PostMapping
    fun create(@RequestBody data: Mono<AdminFoodRequest>): Mono<ResponseEntity<Food>> {
        return data.flatMap {
            create {
                Food(name = it.name).apply {
                    description = it.description
                    primaryImage = it.primaryImage
                    price = it.price
                    categoryId = it.categoryId
                }
            }
        }
    }

}