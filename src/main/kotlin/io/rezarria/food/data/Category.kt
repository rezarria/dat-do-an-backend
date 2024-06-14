package io.rezarria.food.data

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.index.CompoundIndex
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.DocumentReference

@Document
data class Category(var name: String? = null) : Identity() {
    var description: String? = null
    var icon: String? = null
    @JsonSerialize(using= ToStringSerializer::class)
    var foods: List<ObjectId> = emptyList()
}