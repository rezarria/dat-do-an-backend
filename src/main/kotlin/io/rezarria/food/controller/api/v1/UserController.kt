package io.rezarria.food.controller.api.v1

import io.rezarria.food.data.User
import io.rezarria.food.repositories.UserRepository
import io.rezarria.food.service.AuthService
import io.rezarria.food.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/v1/user")
class UserController(private val authService: AuthService) : BaseController<UserService, User, UserRepository>() {

    data class UserRequest(val username: String, val password: String, val name: String)

    @PostMapping
    fun create(data: Mono<UserRequest>): Mono<ResponseEntity<User>> {
        return data.flatMap {
            create {
                User(it.name).apply {
                    username = it.username
                    password = it.password
                }
            }
        }
    }

}