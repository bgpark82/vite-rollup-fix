package com.musinsa.commonwebflux.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.config.CorsRegistry
import org.springframework.web.reactive.config.EnableWebFlux
import org.springframework.web.reactive.config.WebFluxConfigurer

@Configuration
@EnableWebFlux
class WebFluxCorsConfig : WebFluxConfigurer {

    /**
     * CORS 설정
     */
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins("*")
            .allowedMethods(
                "POST", "GET", "OPTIONS", "DELETE",
                "PUT"
            ).allowedHeaders(
                "Content-Type", "Accept"
            )
    }
}
