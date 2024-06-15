package io.rezarria.food.controller.api.v1

import io.rezarria.food.data.Category
import io.rezarria.food.repositories.CategoryRepository
import io.rezarria.food.service.CategoryService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/v1/category")
class CategoryController : BaseController<CategoryService, Category, CategoryRepository>() {
    data class CategoryRequest(val name: String, val description: String, val icon: String)

    @PostMapping
    fun create(data: Mono<CategoryRequest>): Mono<ResponseEntity<Category>> {
        return data.flatMap {
            create {
                Category(it.name).apply {
                    description = it.description
                    icon = it.icon
                }
            }
        }
    }
}
