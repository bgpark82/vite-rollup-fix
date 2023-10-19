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

//    @Autowired
//    lateinit var redisCommands: RedisReactiveCommands<String, String>

    @Autowired
    lateinit var redisConnectionPool: GenericObjectPool<StatefulRedisConnection<String, String>>

    lateinit var redisConnection: StatefulRedisConnection<String, String>

    private val 준비코드_BOOK_KEY = "book"
    private val 준비코드_BOOK_VALUE = """
        {"title":"CPP","author":"Milton","year":"2008","price":"456.00"},{"title":"JAVA","author":"Gilson","year":"2002","price":"456.00"}
    """.trimIndent()
    private val 준비코드_GLOSSARY_KEY = "glossary"
    private val 준비코드_GLOSSARY_VALUE = """
        {"title":"example glossary","GlossDiv":{"title":"S","GlossList":{"GlossEntry":{"ID":"SGML","SortAs":"SGML","GlossTerm":"Standard Generalized Markup Language","Acronym":"SGML","Abbrev":"ISO 8879:1986","GlossDef":{"para":"A meta-markup language, used to create markup languages such as DocBook.","GlossSeeAlso":["GML","XML"]},"GlossSee":"markup"}}}}
    """.trimIndent()

    @BeforeEach
    fun setUp() {
        // 내장 Redis Clear all
//        redisCommands.flushall()

        redisConnection = redisConnectionPool.borrowObject()
        redisConnection.reactive().flushall()
    }

    @AfterEach
    fun clear() {
        // Session 정리
        redisConnection.close()
    }

    @Test
    fun `모든_키_아이템을_가져온다`() {
        runBlocking {
//            redisCommands.set(준비코드_BOOK_KEY, 준비코드_BOOK_VALUE).block()
//            redisCommands.set(준비코드_GLOSSARY_KEY, 준비코드_GLOSSARY_VALUE).block()

            redisConnection.reactive()
                .set(준비코드_BOOK_KEY, 준비코드_BOOK_VALUE).block()
            redisConnection.reactive()
                .set(준비코드_GLOSSARY_KEY, 준비코드_GLOSSARY_VALUE).block()

            val 결과값 = sut.getAll(Search(keys = arrayOf(준비코드_BOOK_KEY, 준비코드_GLOSSARY_KEY)))

            assertThat(결과값).containsExactlyInAnyOrder(
                mapOf(
                    준비코드_BOOK_KEY to ObjectMapperFactory.readValues(
                        준비코드_BOOK_VALUE,
                        typeRefMapAny
                    )
                ),
                mapOf(
                    준비코드_GLOSSARY_KEY to ObjectMapperFactory.readValues(
                        준비코드_GLOSSARY_VALUE,
                        typeRefMapAny
                    )
                )
            )
        }
    }

    @Test
    fun `존재하지_않는_키에_대해서는_빈값을_리스트로_가져온다`() {
        runBlocking {
//            redisCommands.set(준비코드_BOOK_KEY, 준비코드_BOOK_VALUE).block()
            redisConnection.reactive()
                .set(준비코드_BOOK_KEY, 준비코드_BOOK_VALUE).block()

            val 없는_키 = "NON::EXISTENT::KEY"

            val 결과값 = sut.getAll(
                Search(
                    keys = arrayOf(
                        없는_키,
                        준비코드_BOOK_KEY
                    )
                )
            )

            assertThat(결과값).containsExactlyInAnyOrder(
                mapOf(
                    준비코드_BOOK_KEY to ObjectMapperFactory.readValues(
                        준비코드_BOOK_VALUE,
                        typeRefMapAny
                    )
                ),
                mapOf(
                    없는_키 to emptyMap<String, Any>()
                )
            )
        }
    }
}
