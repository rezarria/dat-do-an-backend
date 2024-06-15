package io.rezarria.food.repositories

import io.rezarria.food.data.Food
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface FoodRepository : BaseRepository<Food>, ReactiveMongoRepository<Food, ObjectId>