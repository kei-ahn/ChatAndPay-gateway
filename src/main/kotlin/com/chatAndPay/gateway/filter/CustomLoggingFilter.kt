package com.chatAndPay.gateway.filter

import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.GatewayFilterChain

import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono



@Component
class CustomLoggingFilter : AbstractGatewayFilterFactory<CustomLoggingFilter.Config>(Config::class.java) {

    override fun apply(config: Config): GatewayFilter {
        return GatewayFilter { exchange, chain ->
            val request = exchange.request
            println("Custom pre-filter logic: Request ${request.uri.path}")
            chain.filter(exchange).then(Mono.fromRunnable {
                val response = exchange.response
                println("Custom post-filter logic: Response ${response.statusCode}")
            })
        }
    }

    data class Config(val enabled: Boolean = true)
}