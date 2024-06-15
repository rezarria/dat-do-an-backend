package io.rezarria.food

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class FoodApplication

fun main(args: Array<String>) {
    runApplication<FoodApplication>(*args)
}
