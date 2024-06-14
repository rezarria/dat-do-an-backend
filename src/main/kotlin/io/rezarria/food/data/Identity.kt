package io.rezarria.food.data

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.ser.impl.ObjectIdWriter
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import java.util.UUID

open class Identity {
    @Id
    @JsonSerialize(using= ToStringSerializer::class)
    var id: ObjectId? = null
}