package io.rezarria.food.controller.api.v1

import io.rezarria.food.controller.api.v1.dto.AuthenticationRequest
import io.rezarria.food.controller.api.v1.dto.LoginRequest
import io.rezarria.food.controller.api.v1.dto.UserRegisterRequest
import io.rezarria.food.service.AuthService
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val authService: AuthService
) {

    @PostMapping(path = ["/login"], produces = ["application/json"])
    fun login(@RequestBody authRequest: Mono<LoginRequest>): Mono<ResponseEntity<Any>> {
        return authRequest.flatMap {
            authService.login(it.username, it.password)
        }.map {
            val httpHeaders = HttpHeaders()
            httpHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer ${it.accessToken}")
            ResponseEntity.ok().headers(httpHeaders).body(
                mapOf(
                    "access_token" to it.accessToken,
                    "refresh_token" to it.refreshToken,
                    "roles" to it.roles
                )
            )
        }
    }

    @PostMapping("/register")
    fun register(@RequestBody authRequest: Mono<AuthenticationRequest>): Mono<ResponseEntity<Any>> {
        return authRequest.flatMap {
            authService.register(it.username, it.password, it.name)
        }.map {
            ResponseEntity.ok().body("User created")
        }
    }

    @PostMapping("/user/register")
    fun userRegister(@RequestBody authRequest: Mono<UserRegisterRequest>): Mono<ResponseEntity<String>> {
        return authRequest.flatMap {
            authService.userRegister(it.username, it.password, it.name)
        }.map {
            ResponseEntity.ok().body("User created")
        }.onErrorResume {
            Mono.just(ResponseEntity.badRequest().body(it.message))
        }
    }
}