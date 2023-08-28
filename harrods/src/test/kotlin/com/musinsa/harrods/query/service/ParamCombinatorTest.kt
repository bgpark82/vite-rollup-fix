package com.musinsa.harrods.query.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ParamCombinatorTest {

    @Test
    fun `파라미터의 조합을 생성한다`() {
        val combinator = ParamCombinator()
        val params = mapOf(
            "name" to listOf("peter", "woo"),
            "age" to listOf("10", "20")
        )

        val result = combinator.generate(params)

        assertThat(result).containsExactlyInAnyOrder(
            mapOf("name" to "peter", "age" to "10"),
            mapOf("name" to "peter", "age" to "20"),
            mapOf("name" to "woo", "age" to "10"),
            mapOf("name" to "woo", "age" to "20")
        )
    }

    @Test
    fun `파라미터가 없으면 빈 조합을 생성한다`() {
        val combinator = ParamCombinator()
        val params = null

        val result = combinator.generate(params)

        assertThat(result).containsExactly(mapOf())
    }

    @Test
    fun `파라미터가 비면 빈 조합을 생성한다`() {
        val combinator = ParamCombinator()
        val params = mapOf<String, Any>()

        val result = combinator.generate(params)

        assertThat(result).containsExactly(mapOf())
    }
}
