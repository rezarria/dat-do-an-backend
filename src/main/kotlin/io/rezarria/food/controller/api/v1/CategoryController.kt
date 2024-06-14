package io.rezarria.food.controller.api.v1

import io.rezarria.food.controller.api.v1.dto.AdminFoodRequest
import io.rezarria.food.data.Category
import io.rezarria.food.repositories.CategoryRepository
import io.rezarria.food.service.CategoryService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/v1/category")
class CategoryController : BaseController<CategoryService, Category, CategoryRepository>() {
    override fun create(data: Mono<AdminFoodRequest>): Mono<ResponseEntity<Category>> {
        return data.flatMap { create {
            Category(it.name).apply {
                description = it.description
                icon = it.icon
            }
        } }
    }
}
