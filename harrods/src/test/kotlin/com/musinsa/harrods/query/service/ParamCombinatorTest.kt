package com.musinsa.harrods.query.service

import com.musinsa.common.error.CodeAwareException
import com.musinsa.harrods.error.ErrorCode
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ParamCombinatorTest {

    private val combinator = ParamCombinator()

    @Test
    fun `파라미터의 조합을 생성한다`() {
        val 문자와_숫자_타입_파라미터 = mapOf(
            "name" to listOf("peter", "woo"),
            "age" to listOf(10, 20)
        )

        val result = combinator.generate(문자와_숫자_타입_파라미터)

        assertThat(result).containsExactlyInAnyOrder(
            mapOf("name" to "peter", "age" to 10),
            mapOf("name" to "peter", "age" to 20),
            mapOf("name" to "woo", "age" to 10),
            mapOf("name" to "woo", "age" to 20)
        )
    }

    @Test
    fun `리스트 파라미터를 포함한다`() {
        val 리스트_타입을_포함한_파라미터 = mapOf(
            "name" to listOf("peter", "woo"),
            "age" to listOf(listOf(10, 20), 30)
        )

        val result = combinator.generate(리스트_타입을_포함한_파라미터)

        assertThat(result).containsExactlyInAnyOrder(
            mapOf("name" to "peter", "age" to listOf(10, 20)),
            mapOf("name" to "peter", "age" to 30),
            mapOf("name" to "woo", "age" to listOf(10, 20)),
            mapOf("name" to "woo", "age" to 30)
        )
    }

    @Test
    fun `지원하지 않는 파라미터 타입은 에러를 발생한다`() {
        val 지원하지_않는_파라미터_타입 = mapOf<String, Any>("name" to mapOf("hello" to "world"))

        val result = assertThrows<CodeAwareException> {
            combinator.generate(지원하지_않는_파라미터_타입)
        }

        assertThat(result.error).isEqualTo(ErrorCode.UNSUPPORTED_PARAMETER_TYPE)
    }

    @Test
    fun `파라미터가 없으면 빈 조합을 생성한다`() {
        val null_파라미터 = null

        val result = combinator.generate(null_파라미터)

        assertThat(result).containsExactly(mapOf())
    }

    @Test
    fun `파라미터가 비면 빈 조합을 생성한다`() {
        val 빈_파라미터 = mapOf<String, Any>()

        val result = combinator.generate(빈_파라미터)

        assertThat(result).containsExactly(mapOf())
    }
}
