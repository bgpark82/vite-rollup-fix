package com.musinsa.common.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

//TODO harrods-client 의 경우 coroutine 사용으로 netty framework 사용중이어서, 아래 Cors 설정이 적용 안될것 같다. 확인 필요
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
                "Content-Type", "Accept", "Access-Control-Request-Headers"
            )
    }
}
