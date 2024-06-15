package io.rezarria.food.security.jwt

import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import jakarta.annotation.PostConstruct
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.util.*
import java.util.stream.Collectors.joining
import java.util.stream.Stream
import javax.crypto.SecretKey

@Component
class JwtTokenProvider(private val jwtProperties: JwtProperties) {
    private val logger = LoggerFactory.getLogger(JwtTokenProvider::class.java)
    private lateinit var secretKey: SecretKey

    @PostConstruct
    fun init() {
        val secret = Base64.getEncoder().encodeToString(jwtProperties.secretKey.toByteArray())
        this.secretKey = Keys.hmacShaKeyFor(secret.toByteArray(StandardCharsets.UTF_8))
    }

    fun getRoles(authentication: Authentication): Stream<String> {
        return authentication.authorities.stream()
            .map { obj: GrantedAuthority -> obj.authority }
    }

    fun createToken(authentication: Authentication): String {
        val username: String = authentication.name
        val authorities: Collection<GrantedAuthority> = authentication.authorities
        val claims = Jwts.claims().setSubject(username)
        if (authorities.isNotEmpty()) {
            claims[AUTHORITIES_KEY] = getRoles(authentication).collect(joining(","))
        }
        val now = Date()
        claims.setIssuedAt(now).setExpiration(Date(now.time + jwtProperties.validityInMs))
        return Jwts.builder().addClaims(claims).signWith(this.secretKey, SignatureAlgorithm.HS256).compact()
    }

    fun createRefreshToken(authentication: Authentication): String {
        val username: String = authentication.name
        val authorities: Collection<GrantedAuthority> = authentication.authorities
        val claims = Jwts.claims().setSubject(username)
        if (authorities.isNotEmpty()) {
            claims[AUTHORITIES_KEY] = getRoles(authentication).collect(joining(","))
        }
        val now = Date()
        claims.setIssuedAt(now).setExpiration(Date(now.time + jwtProperties.refreshValidityInMs))
        return Jwts.builder().addClaims(claims).signWith(this.secretKey, SignatureAlgorithm.HS256).compact()
    }

    fun getAuthentication(token: String?): Authentication {
        val claims: Claims = Jwts.parserBuilder().setSigningKey(this.secretKey).build()
            .parseClaimsJws(token).body

        val authoritiesClaim = claims[AUTHORITIES_KEY]

        val authorities: Collection<GrantedAuthority> = if (authoritiesClaim == null
        ) AuthorityUtils.NO_AUTHORITIES
        else AuthorityUtils.commaSeparatedStringToAuthorityList(authoritiesClaim.toString())

        val principal = User(claims.subject, "", authorities)

        return UsernamePasswordAuthenticationToken(principal, token, authorities)
    }

    fun validateToken(token: String?): Boolean {
        try {
            val claims: Jws<Claims> = Jwts.parserBuilder().setSigningKey(this.secretKey)
                .build().parseClaimsJws(token)
            // parseClaimsJws will check expiration date. No need do here.
            logger.warn("expiration date: {}", claims.body.expiration)
            return true
        } catch (e: JwtException) {
            logger.warn("Invalid JWT token: {}", e.message)
        } catch (e: IllegalArgumentException) {
            logger.warn("Invalid JWT token: {}", e.message)
        }
        return false
    }

    companion object {
        const val AUTHORITIES_KEY = "roles"
    }
}