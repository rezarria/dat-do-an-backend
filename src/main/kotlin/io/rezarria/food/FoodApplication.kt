package io.rezarria.food

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity

@SpringBootApplication
@ConfigurationPropertiesScan
class FoodApplication

fun main(args: Array<String>) {
	runApplication<FoodApplication>(*args)
}
