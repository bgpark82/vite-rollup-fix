package com.musinsa.harrods.redis.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories

@Profile(value = ["local", "test"])
@Configuration
@EnableRedisRepositories
class LocalRedisDataSourceConfig(
    @Value("\${spring.data.redis.host}")
    private val CLUSTER_CONFIG_ENDPOINT: String,

    @Value("\${spring.data.redis.port}")
    private val PORT: Int
) {
    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory {
        return LettuceConnectionFactory(CLUSTER_CONFIG_ENDPOINT, PORT)
    }

    @Bean
    fun redisTemplate(): RedisTemplate<ByteArray, ByteArray> {
        val redisTemplate = RedisTemplate<ByteArray, ByteArray>()
        redisTemplate.setConnectionFactory(redisConnectionFactory())
        return redisTemplate
    }
}
