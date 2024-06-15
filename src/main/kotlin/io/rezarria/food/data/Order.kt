package io.rezarria.food.data

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document
data class Order(
    @JsonSerialize(using = ToStringSerializer::class)
    val userId: ObjectId,
    @JsonSerialize(using = ToStringSerializer::class)
    val restaurantId: ObjectId
) : Identity() {
    var foods: List<FoodDetail> = emptyList()
    var status: Status = Status.TEMPORARY
    var address: String? = null
    var phone: String? = null
    var date: Date? = null

    enum class Status {
        PENDING, DELIVERED, CANCELLED, REFUNDED, FAILED, TEMPORARY
    }

    data class FoodDetail(
        @JsonSerialize(using = ToStringSerializer::class) val foodId: ObjectId,
        val count: Int,
        val price: Double
    )
}

