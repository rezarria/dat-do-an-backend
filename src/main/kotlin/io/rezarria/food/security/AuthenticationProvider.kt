package io.rezarria.food.security

import io.rezarria.food.security.jwt.JwtTokenProvider
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono


@Component
class AuthenticationProvider(private val jwtTokenProvider: JwtTokenProvider) {

    data class Info(val username: String, val authorities: List<String>)

    private fun info(authentication: Mono<Authentication>): Mono<Info> {
        return authentication.map {
            val username: String = it.name
            val roles = jwtTokenProvider.getRoles(it).toList()
            Info(username, roles)
        }
    }

    private fun getAuthentication(): Mono<Authentication> {
        return ReactiveSecurityContextHolder.getContext()
            .map { it.authentication }
    }

    val info: Mono<Info>
        get() {
            return info(getAuthentication())
        }

}