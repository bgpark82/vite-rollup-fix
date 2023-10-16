package com.musinsa.common.redis.config

import io.lettuce.core.RedisClient
import io.lettuce.core.RedisURI
import io.lettuce.core.api.StatefulRedisConnection
import io.lettuce.core.support.ConnectionPoolSupport
import org.apache.commons.pool2.impl.GenericObjectPool
import org.apache.commons.pool2.impl.GenericObjectPoolConfig
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.DependsOn
import org.springframework.context.annotation.Profile

@Profile(value = ["local", "test"])
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
    @DependsOn("localRedisServer")
    fun redisConnectionPool(): GenericObjectPool<StatefulRedisConnection<String, String>> {
        val redisClient =
            RedisClient.create(
                RedisURI.Builder.redis(CLUSTER_CONFIG_ENDPOINT).withPort(PORT)
                    .build()
            )

        // Connection Pool 생성 후 리턴
        return ConnectionPoolSupport
            .createGenericObjectPool(
                { redisClient.connect() },
                GenericObjectPoolConfig()
            )
    }
}
