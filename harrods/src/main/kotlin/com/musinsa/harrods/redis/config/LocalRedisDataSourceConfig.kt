package com.musinsa.harrods.redis.config

import io.lettuce.core.RedisClient
import io.lettuce.core.RedisURI
import io.lettuce.core.api.StatefulConnection
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Profile(value = ["local", "test"])
@Suppress("PrivatePropertyName")
@Configuration
class LocalRedisDataSourceConfig(
    @Value("\${spring.data.redis.host}")
    private val CLUSTER_CONFIG_ENDPOINT: String,

    @Value("\${spring.data.redis.port}")
    private val PORT: Int
) {

    /**
     * Local, Test 실행을 위한 내장 Redis
     */
    @Bean
    fun redisConnection(): StatefulConnection<String, String> {
        val redisClient =
            RedisClient.create(
                RedisURI.Builder.redis(CLUSTER_CONFIG_ENDPOINT).withPort(PORT)
                    .build()
            )

        return redisClient.connect()
    }
}
