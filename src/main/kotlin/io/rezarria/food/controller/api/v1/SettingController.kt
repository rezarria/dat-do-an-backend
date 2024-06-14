package io.rezarria.food.controller.api.v1

import io.rezarria.food.service.AuthService
import io.rezarria.food.service.CategoryService
import io.rezarria.food.service.FoodService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/v1/setting")
class SettingController(
    private val authService: AuthService,
    private val categoryService: CategoryService,
    private val foodService: FoodService
) {


    @GetMapping("/init")
    fun init(): Mono<Void> {
        val list = listOf(
            categoryService.add("Pizza")
                .flatMapMany {
                    Flux.fromIterable(
                        listOf(
                            foodService.add("Pizza Hawail", 100.0, "Pizza", null, it.id),
                            foodService.add("Pizza Seafood", 120.0, "Pizza", null, it.id),
                            foodService.add("Pizza Chicken", 110.0, "Pizza", null, it.id),
                            foodService.add("Pizza Beef", 110.0, "Pizza", null, it.id),
                            foodService.add("Pizza Vegetarian", 90.0, "Pizza", null, it.id)
                        )
                    ).flatMap { it }
                },
            categoryService.add("Burger")
                .flatMapMany {
                    Flux.fromIterable(
                        listOf(
                            foodService.add("Burger Chicken", 50.0, "Burger", null, it.id),
                            foodService.add("Burger Beef", 60.0, "Burger", null, it.id),
                            foodService.add("Burger Fish", 70.0, "Burger", null, it.id),
                            foodService.add("Burger Vegetarian", 40.0, "Burger", null, it.id)
                        )
                    ).flatMap { it }
                },
            categoryService.add("Drink")
                .flatMapMany {
                    Flux.fromIterable(
                        listOf(
                            foodService.add("Coca Cola", 10.0, "Drink", null, it.id),
                            foodService.add("Pepsi", 10.0, "Drink", null, it.id),
                            foodService.add("7Up", 10.0, "Drink", null, it.id),
                            foodService.add("Fanta", 10.0, "Drink", null, it.id),
                            foodService.add("Sprite", 10.0, "Drink", null, it.id)
                        )
                    ).flatMap { it }
                },
            categoryService.add("Dessert")
                .flatMapMany {
                    Flux.fromIterable(
                        listOf(
                            foodService.add("Ice Cream", 20.0, "Dessert", null, it.id),
                            foodService.add("Cake", 30.0, "Dessert", null, it.id),
                            foodService.add("Pudding", 25.0, "Dessert", null, it.id),
                            foodService.add("Jelly", 15.0, "Dessert", null, it.id)
                        )
                    ).flatMap { it }
                },
            categoryService.add("Fast Food")
                .flatMapMany {
                    Flux.fromIterable(
                        listOf(
                            foodService.add("French Fries", 20.0, "Fast Food", null, it.id),
                            foodService.add("Onion Rings", 25.0, "Fast Food", null, it.id),
                            foodService.add("Chicken Wings", 30.0, "Fast Food", null, it.id),
                            foodService.add("Chicken Nuggets", 25.0, "Fast Food", null, it.id)
                        )
                    ).flatMap { it }
                }
        )
        return authService.register("admin", "admin", listOf("ROLE_ADMIN"))
            .flatMap {
                Flux.merge(Flux.fromIterable(list)).then()
            }
    }
}