package com.musinsa.harrods.redis.config

import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import redis.embedded.RedisServer

/**
 * 로컬, 테스트 환경에서 서비스 실행을 위해 Redis 클러스터 구현
 */
@Profile(value = ["local", "test"])
@Suppress("PrivatePropertyName")
@Configuration
class LocalRedisServer(
    @Value("\${spring.data.redis.port}")
    private val PORT: Int
) {
    private val redisServer: RedisServer = RedisServer(PORT)

    /**
     * Bean 생성 직후, 내장 Redis 시작
     */
    @PostConstruct
    private fun startRedis() {
        redisServer.start()
    }

    /**
     * 스프링컨테이너에서 Bean 삭제 전, 내장 Redis 종료
     */
    @PreDestroy
    private fun stopRedis() {
        redisServer.stop()
    }
}
