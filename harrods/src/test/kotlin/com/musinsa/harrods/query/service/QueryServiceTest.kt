package com.musinsa.harrods.query.service

import com.musinsa.harrods.query.dto.QueryRequest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class QueryServiceTest {

    private val paramCombinator = ParamCombinator()
    private val keyCreator = KeyCreator()
    private val queryService = QueryService(paramCombinator, keyCreator)

    @Test
    fun `단일 파라미터로 쿼리를 생성한다`() {
        val request = QueryRequest(
            template = "SELECT * FROM user WHERE name = {{name}} AND age = {{age}}",
            params = mapOf("name" to "peter", "age" to "30"),
            ttl = 300,
            schedule = "* * * *"
        )

        val result = queryService.create(request)

        assertThat(result[0].queries).isEqualTo("SELECT * FROM user WHERE name = peter AND age = 30")
    }

    @Test
    fun `복수 파라미터로 쿼리를 생성한다`() {
        val request = QueryRequest(
            template = "SELECT * FROM user WHERE name = {{name}} AND age = {{age}}",
            params = mapOf("name" to listOf("peter", "woo"), "age" to listOf("30")),
            ttl = 300,
            schedule = "* * * *"
        )

        val result = queryService.create(request)

        assertThat(result[0].queries).isEqualTo("SELECT * FROM user WHERE name = peter AND age = 30")
        assertThat(result[1].queries).isEqualTo("SELECT * FROM user WHERE name = woo AND age = 30")
    }
}
