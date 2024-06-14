package io.rezarria.food.repositories

import org.springframework.data.domain.PageRequest
import reactor.core.publisher.Flux

fun <T> BaseRepository<T>.createPage(page: Int, size: Int): Flux<T> {
    return this.page(PageRequest.of(page, size))
}