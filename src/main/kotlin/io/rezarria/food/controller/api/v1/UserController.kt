package io.rezarria.food.controller.api.v1

import io.rezarria.food.service.AuthService
import io.rezarria.food.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/v1/user")
class UserController(private val authService: AuthService, private val userService: UserService) {


    data class DeleteRequest(val username: String)

    @PostMapping("/test/delete")
    fun deleteUser(@RequestBody data: DeleteRequest): Mono<ResponseEntity<Any>> {
        return userService.deleteByUsername(data.username)
            .then(Mono.just(ResponseEntity.ok().build()))
    }

    @GetMapping("/userinfo")
    fun getUserInfo(): Mono<ResponseEntity<Any>> {
        return userService.getCurrentUser()
            .map {
                ResponseEntity.ok().body(it)
            }
    }

    data class UpdateRequest(val name: String)

    @PutMapping("/userinfo")
    fun updateUserInfo(@RequestBody data: UpdateRequest): Mono<ResponseEntity<Any>> {
        return userService.updateCurrentUser(data.name)
            .map {
                ResponseEntity.ok().body(it)
            }
    }

}