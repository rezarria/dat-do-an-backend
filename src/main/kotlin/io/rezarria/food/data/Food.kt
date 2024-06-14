package io.rezarria.food.data

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.index.CompoundIndex
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import org.springframework.data.mongodb.core.mapping.FieldType

@Document
@CompoundIndex(name = "restaurant_food", def = "{'restaurantId': 1}")
data class Food(var name:String?) : Identity() {
    var price: Double = 0.0
    @JsonSerialize(using= ToStringSerializer::class)
    var categoryId: ObjectId? = null
    var description: String? = null
    var primaryImage: String? = null
    var images: List<String> = emptyList()
}

