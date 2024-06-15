package io.rezarria.food.repositories

import io.rezarria.food.data.Order
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface OrderRepository : BaseRepository<Order>, ReactiveMongoRepository<Order, ObjectId>