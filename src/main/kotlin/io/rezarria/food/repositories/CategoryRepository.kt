package io.rezarria.food.repositories

import io.rezarria.food.data.Category
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.Aggregation
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface CategoryRepository : BaseRepository<Category>, ReactiveMongoRepository<Category, ObjectId>{


}