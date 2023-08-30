package com.musinsa.harrods.query.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class QueryGeneratorTest {

    private val queryGenerator = QueryGenerator()

    @Test
    fun `문자 타입 파라미터로 쿼리를 생성한다`() {
        val query = "SELECT * FROM user WHERE name = {{name}} AND age = {{age}}"
        val param = mapOf("name" to "peter")

        val result = queryGenerator.generate(query, param)

        assertThat(result).isEqualTo("SELECT * FROM user WHERE name = peter AND age = {{age}}")
    }

    @Test
    fun `숫자 타입 파라미터로 쿼리를 생성한다`() {
        val query = "SELECT * FROM user WHERE name = {{name}} AND age = {{age}}"
        val param = mapOf("age" to 30)

        val result = queryGenerator.generate(query, param)

        assertThat(result).isEqualTo("SELECT * FROM user WHERE name = {{name}} AND age = 30")
    }

    @Test
    fun `리스트 타입 파라미터로 쿼리를 생성한다`() {
        val query = "SELECT * FROM user WHERE age in ({{age}})"
        val param = mapOf("age" to listOf(1, 2))

        val result = queryGenerator.generate(query, param)

        assertThat(result).isEqualTo("SELECT * FROM user WHERE age in (1,2)")
    }
}
