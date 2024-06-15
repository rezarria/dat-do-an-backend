package io.rezarria.food.service

import io.rezarria.food.data.Order
import io.rezarria.food.repositories.FoodRepository
import io.rezarria.food.repositories.OrderRepository
import io.rezarria.food.repositories.UserRepository
import io.rezarria.food.security.AuthenticationProvider
import org.springframework.stereotype.Service

@Service
class OrderService(
    private val auth: AuthenticationProvider,
    private val userRepository: UserRepository,
    private val foodRepository: FoodRepository
) : BaseDataService<Order, OrderRepository>()
