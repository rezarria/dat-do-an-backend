package io.rezarria.food.service

import io.rezarria.food.data.User
import io.rezarria.food.repositories.UserRepository
import io.rezarria.food.security.AuthenticationProvider
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono


@Service
class UserService(private val auth: AuthenticationProvider) : BaseDataService<User, UserRepository>() {
    fun deleteByUsername(username: String): Mono<Void> {
        return repository.deleteByUsername(username)
    }

    fun getCurrentUser(): Mono<User> {
        return auth.info.flatMap { repository.findByUsername(it.username) }
    }

    fun updateCurrentUser(name: String): Mono<User> {
        return auth.info.flatMap { repository.findByUsername(it.username) }
            .map { it ?: throw IllegalArgumentException("Value cannot be null") }
            .flatMap {
                it.name = name
                repository.save(it)
            }
    }

}