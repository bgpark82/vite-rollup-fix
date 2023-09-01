package com.musinsa.harrods.query.domain

import com.musinsa.common.redis.config.LocalRedisServer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.mock.mockito.MockBean

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // DataJpaTest의 Embedded 데이터베이스가 아닌 현재 설정된 데이터베이스 사용 (h2)
class QueryRepositoryTest @Autowired constructor(
    val queryRepository: QueryRepository,
    @MockBean val redisServer: LocalRedisServer
) {

    @Test
    fun `생성된 쿼리를 저장한다`() {
        val query = "SELECT * FROM user"
        val key = "harrods:${query.hashCode()}"
        val interval = "* * * *"
        val ttl = 1000L

        val savedQuery =
            queryRepository.save(Query.create(query, key, ttl, interval))

        assertThat(savedQuery.id).isEqualTo(1L)
        assertThat(savedQuery.ttl).isEqualTo(ttl)
        assertThat(savedQuery.queries).isEqualTo(query)
        assertThat(savedQuery.cacheKey).isEqualTo(key)
        assertThat(savedQuery.scheduleInterval).isEqualTo(interval)
    }
}
