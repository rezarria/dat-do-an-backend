package io.rezarria.food.data

import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document
data class Coupon(
    var name: String? = null,
    var code: String? = null,
    var validAt: Date? = null,
    var expire: Date? = null,
) : Identity() {
}