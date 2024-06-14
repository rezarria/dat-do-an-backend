package io.rezarria.food.config

import io.rezarria.food.repositories.UserRepository
import io.rezarria.food.security.CustomUserDetailsService
import io.rezarria.food.security.jwt.JwtTokenAuthenticationFilter
import io.rezarria.food.security.jwt.JwtTokenProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager
import org.springframework.security.authorization.AuthorizationDecision
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.authorization.AuthorizationContext
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository
import reactor.core.publisher.Mono


@Configuration
class Security {

    @Bean
    fun springWebFilterChain(
        http: ServerHttpSecurity,
        tokenProvider: JwtTokenProvider?,
        reactiveAuthenticationManager: ReactiveAuthenticationManager?
    ): SecurityWebFilterChain {

        return http.csrf { it.disable() }
            .httpBasic { it.disable() }
            .authenticationManager(reactiveAuthenticationManager)
            .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
            .authorizeExchange {
                it
                    .pathMatchers("/api/v1/hello").authenticated()
                    .pathMatchers(HttpMethod.POST, "/api/v1/auth/login").permitAll()
                    .pathMatchers("/users/{user}/**").access(this::currentUserMatchesPath)
                    .anyExchange().permitAll()
            }
            .addFilterAt(JwtTokenAuthenticationFilter(tokenProvider!!), SecurityWebFiltersOrder.HTTP_BASIC)
            .build()
    }

    private fun currentUserMatchesPath(
        authentication: Mono<Authentication>,
        context: AuthorizationContext
    ): Mono<AuthorizationDecision> {
        return authentication
            .map { a: Authentication ->
                context.variables["user"] == a.name
            }
            .map { granted: Boolean? ->
                AuthorizationDecision(
                    granted!!
                )
            }
    }

    @Bean
    fun userDetailsService(users: UserRepository): ReactiveUserDetailsService {
        return CustomUserDetailsService(users)
    }

    @Bean
    fun reactiveAuthenticationManager(
        userDetailsService: ReactiveUserDetailsService?,
        passwordEncoder: PasswordEncoder?
    ): ReactiveAuthenticationManager {
        val authenticationManager = UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService)
        authenticationManager.setPasswordEncoder(passwordEncoder)
        return authenticationManager
    }
}