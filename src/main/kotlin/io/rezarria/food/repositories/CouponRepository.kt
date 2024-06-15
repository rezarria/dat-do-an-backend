package io.rezarria.food.repositories

import io.rezarria.food.data.Coupon
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface CouponRepository : BaseRepository<Coupon>, ReactiveMongoRepository<Coupon, ObjectId>