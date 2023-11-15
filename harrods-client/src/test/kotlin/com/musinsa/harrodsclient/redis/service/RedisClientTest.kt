package com.musinsa.harrodsclient.redis.service

import com.musinsa.common.util.ObjectMapperFactory
import com.musinsa.common.util.ObjectMapperFactory.typeRefMapAny
import com.musinsa.harrodsclient.redis.dto.Search
import io.lettuce.core.api.StatefulRedisConnection
import kotlinx.coroutines.runBlocking
import org.apache.commons.pool2.impl.GenericObjectPool
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
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
    lateinit var redisConnectionPool: GenericObjectPool<StatefulRedisConnection<String, String>>

    lateinit var redisConnection: StatefulRedisConnection<String, String>

    private val 준비코드_KEY_1 = "harrods:woo.choi:1355159473:goods_no:2559251"
    private val 준비코드_VALUE_1 = """
        {"key": "harrods:woo.choi:1355159473:goods_no:2559251", "value": {"goods_no": "2559251", "age_band.0": 0, "age_band.19": 0, "age_band.24": 2, "age_band.29": 1, "age_band.34": 2, "age_band.40": 1, "age_band.N": 1, "gender.F": 4, "gender.M": 2, "gender.N": 1, "total": 7}}
    """.trimIndent()
    private val 준비코드_KEY_2 =
        "harrods:woo.choi:1355159473:goods_no:2515178"
    private val 준비코드_VALUE_2 = """
        {"key": "harrods:woo.choi:1355159473:goods_no:2515178", "value": {"goods_no": "2515178", "age_band.0": 5, "age_band.19": 12, "age_band.24": 5, "age_band.29": 7, "age_band.34": 4, "age_band.40": 1, "age_band.N": 7, "gender.F": 33, "gender.M": 1, "gender.N": 7, "total": 41}}
    """.trimIndent()

    @BeforeEach
    fun setUp() {
        redisConnection = redisConnectionPool.borrowObject()
        redisConnection.reactive().flushall()
    }

    @AfterEach
    fun clear() {
        // Session 정리
        redisConnection.close()
    }

    @Test
    fun `모든 키 아이템을 가져온다`() {
        runBlocking {
            redisConnection.reactive()
                .set(준비코드_KEY_1, 준비코드_VALUE_1).block()
            redisConnection.reactive()
                .set(준비코드_KEY_2, 준비코드_VALUE_2).block()

            val 결과값 = sut.getAll(
                Search(
                    keys = arrayOf(
                        준비코드_KEY_1,
                        준비코드_KEY_2
                    )
                )
            )

            assertThat(결과값).containsExactlyInAnyOrder(
                ObjectMapperFactory.readValues(
                    준비코드_VALUE_1,
                    typeRefMapAny
                ),
                ObjectMapperFactory.readValues(
                    준비코드_VALUE_2,
                    typeRefMapAny
                )
            )
        }
    }

    @Test
    fun `존재하지 않는 키에 대해서는 빈값을 리스트로 가져온다`() {
        runBlocking {
            redisConnection.reactive()
                .set(준비코드_KEY_1, 준비코드_VALUE_1).block()

            val 없는_키 = "NON::EXISTENT::KEY"

            val 결과값 = sut.getAll(
                Search(
                    keys = arrayOf(
                        없는_키,
                        준비코드_KEY_1
                    )
                )
            )

            assertThat(결과값).containsExactlyInAnyOrder(
                ObjectMapperFactory.readValues(
                    준비코드_VALUE_1,
                    typeRefMapAny
                ),
                mapOf(
                    KEY to 없는_키,
                    VALUE to emptyMap<String, Any>()
                )
            )
        }
    }
}
