package io.rezarria.food.security.jwt

import org.springframework.http.HttpHeaders
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.util.StringUtils
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

class JwtTokenAuthenticationFilter(private val tokenProvider: JwtTokenProvider) : WebFilter {
    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        val token = resolveToken(exchange.request)
        if (StringUtils.hasText(token) && tokenProvider.validateToken(token)) {
            return Mono.fromCallable {
                tokenProvider.getAuthentication(
                    token
                )
            }
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap { authentication ->
                    chain.filter(exchange)
                        .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication))
                }
        }
        return chain.filter(exchange)
    }

    private fun resolveToken(request: ServerHttpRequest): String? {
        val bearerToken = request.headers.getFirst(HttpHeaders.AUTHORIZATION)
        if (bearerToken != null && StringUtils.hasText(bearerToken) && bearerToken.startsWith(HEADER_PREFIX)) {
            return bearerToken.substring(7)
        }
        return null
    }

    companion object {
        const val HEADER_PREFIX: String = "Bearer "
    }
}