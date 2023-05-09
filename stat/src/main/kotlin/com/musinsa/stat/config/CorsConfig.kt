package com.musinsa.stat.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class CorsConfig : WebMvcConfigurer {

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