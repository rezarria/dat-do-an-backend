package io.rezarria.food.service

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.fge.jsonpatch.JsonPatch
import io.rezarria.food.repositories.BaseRepository
import io.rezarria.food.repositories.createPage
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Mono


abstract class BaseDataService<T, R> where R : ReactiveMongoRepository<T, ObjectId>, R : BaseRepository<T> {
    @Autowired
    protected lateinit var repository: R
    open fun create(data: T & Any): Mono<T> {
        return repository.save(data)
    }

    @Autowired
    protected lateinit var objectMapper: ObjectMapper

    open fun size(): Mono<Long> {
        return repository.count()
    }

    open fun page(page: Int? = null, size: Int? = null): Mono<PageImpl<T>> {
        val pageNumber = page ?: 0
        val pageSize = size ?: 10
        return size().zipWith(repository.createPage(pageNumber, pageSize).collectList()).map {
            PageImpl(it.t2, PageRequest.of(pageNumber, pageSize), it.t1)
        }
    }

    open fun page(pageable: Pageable): Mono<PageImpl<T>> {
        return size().zipWith(repository.page(pageable).collectList()).map {
            PageImpl(it.t2, pageable, it.t1)
        }
    }

    open fun update(patch: JsonPatch, id: ObjectId): Mono<T> {
        return repository.findById(id).flatMap {
            val obj = objectMapper.convertValue(it, JsonNode::class.java)
            val patched = patch.apply(obj)
            repository.save(objectMapper.treeToValue(patched, it!!::class.java))
        }
    }

    open fun update(id: ObjectId, dispatcher: (T) -> Unit): Mono<T> {
        return repository.findById(id).map {
            dispatcher(it)
            return@map it
        }
    }

    open fun get(id: ObjectId): Mono<T> {
        return repository.findById(id)
    }

    open fun delete(id: ObjectId): Mono<Void> {
        return repository.deleteById(id)
    }
}