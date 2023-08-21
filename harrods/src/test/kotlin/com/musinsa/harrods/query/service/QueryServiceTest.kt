package com.musinsa.harrods.query.service

import com.musinsa.harrods.query.dto.QueryRequest
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test

class QueryServiceTest {

    @Test
    fun `단일 파라미터로 쿼리를 생성한다`() {
        val request = QueryRequest(
            template = "SELECT * FROM user WHERE name = {{name}} AND age = {{age}}",
            params = mapOf("name" to "peter", "age" to "30")
        )

        val result = QueryService().create(request)

        assertThat(result).isEqualTo("SELECT * FROM user WHERE name = peter AND age = 30")
    }
}
