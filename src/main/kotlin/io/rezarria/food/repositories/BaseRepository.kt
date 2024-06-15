package io.rezarria.food.repositories

import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.Query
import reactor.core.publisher.Flux

interface BaseRepository<T> {
    @Query("{}")
    fun page(pageable: Pageable): Flux<T>
}