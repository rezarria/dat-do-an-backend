package io.rezarria.food.repositories

import io.rezarria.food.data.User
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.ExistsQuery
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Mono

interface UserRepository : BaseRepository<User>, ReactiveMongoRepository<User, ObjectId> {
    fun findByUsername(username: String): Mono<User?>
    fun deleteByUsername(username: String): Mono<Void>

    @ExistsQuery("{ 'username' : ?0 }")
    fun existsByUsername(username: String): Mono<Boolean>
    fun findByEmail(email: String): Mono<User?>
}