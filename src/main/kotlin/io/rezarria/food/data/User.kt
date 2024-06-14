package io.rezarria.food.data

import org.springframework.data.mongodb.core.index.CompoundIndex
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document
class User(var name:String?): Identity() {
    var email : String? = null
    @Indexed(unique = true)
    var username : String? = null
    var password : String? = null
    var roles : List<String> = listOf()
}