package com.musinsa.harrodsclient.redis.service

import io.lettuce.core.api.sync.RedisCommands
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

/**
 * Redis Client Service Test
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
internal class RedisClientTest {
    @Autowired
    lateinit var redisClient: RedisClient

    @Autowired
    lateinit var redisCommands: RedisCommands<String, String>

    @BeforeEach
    fun setUp() {
        // 내장 Redis Clear all
        redisCommands.flushall()
    }

    @Test
    fun 아이템을_가져온다() {
        // given
        redisCommands.set("test-key", "value")

        // when
        val test = redisClient.get("test-key")

        // then
        assertThat(test).isEqualTo("value")
    }
}
