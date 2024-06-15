package io.rezarria.food.repositories

import io.rezarria.food.data.Category
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface CategoryRepository : BaseRepository<Category>, ReactiveMongoRepository<Category, ObjectId>