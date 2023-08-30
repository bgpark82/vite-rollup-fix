package com.musinsa.harrodsclient.redis.service

import com.musinsa.common.util.ObjectMapperFactory
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
    lateinit var sut: RedisClient

    @Autowired
    lateinit var redisCommands: RedisCommands<String, String>

    private val 준비코드_BOOK_KEY = "book"
    private val 준비코드_BOOK_VALUE = """
            [{"title":"CPP","author":"Milton","year":"2008","price":"456.00"},{"title":"JAVA","author":"Gilson","year":"2002","price":"456.00"}]
    """.trimIndent()

    @BeforeEach
    fun setUp() {
        // 내장 Redis Clear all
        redisCommands.flushall()
    }

    @Test
    fun 아이템을_가져온다() {
        redisCommands.set(준비코드_BOOK_KEY, 준비코드_BOOK_VALUE)

        val 결과값 = sut.get(준비코드_BOOK_KEY)

        assertThat(ObjectMapperFactory.writeValueAsString(결과값)).isEqualTo(
            준비코드_BOOK_VALUE
        )
    }

    @Test
    fun 모든_키_아이템을_가져온다() {
        // given

        // when

        // then
    }
}
