package io.rezarria.food.service

import io.rezarria.food.data.Category
import io.rezarria.food.repositories.CategoryRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class CategoryService : BaseDataService<Category, CategoryRepository>() {

    fun add(name: String, description: String? = null, icon: String? = null): Mono<Category> {
        return repository.save(Category(name).apply {
            this.description = description
            this.icon = icon
        })
    }

    fun addAll(vararg name: String): Flux<Category> {
        return repository.saveAll(name.map { Category(it) })
    }
}