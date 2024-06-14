package io.rezarria.food.service

import io.rezarria.food.data.User
import io.rezarria.food.repositories.UserRepository
import io.rezarria.food.security.jwt.JwtTokenProvider
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class AuthService(
    private val jwtTokenProvider: JwtTokenProvider,
    private val reactiveAuthenticationManager: ReactiveAuthenticationManager,
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {
    fun login(username: String, password: String): Mono<Token> {
        return reactiveAuthenticationManager.authenticate(UsernamePasswordAuthenticationToken(username, password))
            .map {
                Token(
                    jwtTokenProvider.createToken(it),
                    jwtTokenProvider.createRefreshToken(it),
                    jwtTokenProvider.getRoles(it).toList()
                )
            }
    }

    fun register(username: String, password: String, name: String): Mono<User> {
        return register(username, password, listOf("ROLE_USER"), name = name)
    }

    fun register(username: String, password: String, roles: List<String>, name: String? = null): Mono<User> {
        return userRepository.existsByUsername(username)
            .flatMap {
                if (it) {
                    throw Exception("Người dùng đã tồn tại")
                } else {
                    userRepository.save(User(username).apply {
                        this.username = username
                        this.roles = roles
                        this.name = name
                        this.password = passwordEncoder.encode(password)
                    })
                }
            }
    }

    fun userRegister(username: String, password: String, name: String): Mono<User> {
        return userRepository.existsByUsername(username)
            .flatMap {
                if (it) {
                    throw Exception("Người dùng đã tồn tại")
                } else {
                    userRepository.save(User(name = name).apply {
                        this.username = username
                        this.roles = listOf("ROLE_USER")
                        this.password = passwordEncoder.encode(password)
                    })
                }
            }
    }

    data class Token(
        val accessToken: String,
        val refreshToken: String,
        val roles: List<String>
    )
}