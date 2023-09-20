package com.musinsa.harrods.query.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // DataJpaTest의 Embedded 데이터베이스가 아닌 현재 설정된 데이터베이스 사용 (h2)
class QueryRepositoryTest @Autowired constructor(
    val queryRepository: QueryRepository
) {

    @Test
    fun `생성된 쿼리를 저장한다`() {
        val query = "SELECT user.brand as brand FROM user"
        val key = "harrods:${query.hashCode()}"
        val interval = "* * * *"
        val ttl = 1000L
        val userId = "peter.park"
        val cacheKeySuffix = listOf("brand")

        val savedQuery =
            queryRepository.save(Query.create(query, key, ttl, interval, userId, cacheKeySuffix))

        assertThat(savedQuery.ttl).isEqualTo(ttl)
        assertThat(savedQuery.query).isEqualTo(query)
        assertThat(savedQuery.cacheKey).isEqualTo(key)
        assertThat(savedQuery.scheduleInterval).isEqualTo(interval)
        assertThat(savedQuery.cacheKeySuffix).containsAll(cacheKeySuffix)
    }

    @ParameterizedTest
    @ValueSource(strings = ["harrods:123"])
    fun `키가 존재하는지 확인한다`(이미_존재하는_키: String) {
        queryRepository.save(
            Query.create(
                queries = "SELECT user.brand as brand FROM user",
                cacheKey = 이미_존재하는_키,
                ttl = 1000L,
                scheduleInterval = "* * * *",
                userId = "peter.park",
                cacheKeySuffix = listOf("brand")
            )
        )

        val isExist = queryRepository.existsByCacheKeyIn(listOf(이미_존재하는_키))

        assertThat(isExist).isTrue()
    }
}
