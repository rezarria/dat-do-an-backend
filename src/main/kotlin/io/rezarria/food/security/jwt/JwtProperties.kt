package io.rezarria.food.security.jwt

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "jwt")
class JwtProperties {
    var secretKey: String = "rzxlszyykpbgqcflzxsqcysyhljt"
    var validityInMs: Long = 3600000
    var refreshValidityInMs: Long = 86400000
}