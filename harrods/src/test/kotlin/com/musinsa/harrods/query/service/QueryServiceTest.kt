package com.musinsa.harrods.query.service

import com.musinsa.harrods.query.dto.QueryRequest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class QueryServiceTest {

    private val paramCombinator = ParamCombinator()
    private val queryService = QueryService(paramCombinator)

    @Test
    fun `단일 파라미터로 쿼리를 생성한다`() {
        val request = QueryRequest(
            template = "SELECT * FROM user WHERE name = {{name}} AND age = {{age}}",
            params = mapOf("name" to "peter", "age" to "30")
        )

        val result = queryService.create(request)

        assertThat(result).contains("SELECT * FROM user WHERE name = peter AND age = 30")
    }

    @Test
    fun `복수 파라미터로 쿼리를 생성한다`() {
        val request = QueryRequest(
            template = "SELECT * FROM user WHERE name = {{name}} AND age = {{age}}",
            params = mapOf("name" to listOf("peter", "woo"), "age" to listOf("30"))
        )

        val result = queryService.create(request)

        assertThat(result).contains(
            "SELECT * FROM user WHERE name = peter AND age = 30",
            "SELECT * FROM user WHERE name = woo AND age = 30"
        )
    }
}
