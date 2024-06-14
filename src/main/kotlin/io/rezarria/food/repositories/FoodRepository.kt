package io.rezarria.food.repositories

import io.rezarria.food.data.Food
import org.bson.types.ObjectId
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.Aggregation
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface FoodRepository : BaseRepository<Food>, ReactiveMongoRepository<Food, ObjectId> {


}