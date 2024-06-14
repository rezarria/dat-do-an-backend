package io.rezarria.food.security

import io.rezarria.food.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.UserDetails
import reactor.core.publisher.Mono

class CustomUserDetailsService(private val userRepository: UserRepository) : ReactiveUserDetailsService {
    override fun findByUsername(username: String): Mono<UserDetails> {
        return userRepository.findByUsername(username)
            .map { it ?: throw IllegalArgumentException("User not found") }
            .map { CustomUserDetails(it) }
    }
}