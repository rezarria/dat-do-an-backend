package io.rezarria.food.controller.api.v1

import com.github.fge.jsonpatch.JsonPatch
import io.rezarria.food.controller.api.v1.dto.AdminFoodRequest
import io.rezarria.food.controller.models.PageParam
import io.rezarria.food.repositories.BaseRepository
import io.rezarria.food.service.BaseDataService
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageImpl
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

abstract class BaseController<S, T, R> where S : BaseDataService<T, R>, R : ReactiveMongoRepository<T, ObjectId>, R : BaseRepository<T> {
    @Autowired
    protected lateinit var service: S

    @GetMapping("/detail/{id}")
    fun getDetail(@PathVariable id: ObjectId): Mono<ResponseEntity<T>> {
        return service.get(id).map { ResponseEntity.ok(it) }
    }

    @DeleteMapping("/{id}")
    fun xoá(@PathVariable id: ObjectId): Mono<ResponseEntity<Void>> {
        return service.delete(id).map { ResponseEntity.ok(it) }
    }

    @PatchMapping("/{id}")
    fun `cập nhật`(@PathVariable id: ObjectId, @RequestBody patch: JsonPatch): Mono<ResponseEntity<T>> {
        return service.update(patch, id).map {
            ResponseEntity.ok(it)
        }
    }

    @GetMapping
    fun page(@ModelAttribute page: PageParam): Mono<ResponseEntity<PageImpl<T>>> {
        return service.page(page.page, page.size).map {
            ResponseEntity.ok().body(it)
        }
    }

    @PostMapping
    abstract fun create(@RequestBody data: Mono<AdminFoodRequest>): Mono<ResponseEntity<T>>

    open fun create(action: () -> T & Any): Mono<ResponseEntity<T>> {
        return service.create(action.invoke()).map {
            ResponseEntity.ok(it)
        }
    }
}