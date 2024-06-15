package io.rezarria.food.data

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id

open class Identity {
    @Id
    @JsonSerialize(using = ToStringSerializer::class)
    var id: ObjectId? = null
}