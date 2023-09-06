package com.musinsa.harrods.query.service

import com.musinsa.common.error.CodeAwareException
import com.musinsa.harrods.error.ErrorCode
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class KeyGeneratorTest {

    private val keyGenerator = KeyGenerator()

    @Test
    fun `문자, 숫자 파라미터로 키를 생성한다`() {
        val query = "SELECT * FROM user WHERE name = 'peter' and age = 10"
        val 문자_숫자_타입_파라미터 = mapOf<String, Any>("name" to "peter", "age" to 10)

        val key = keyGenerator.generate(
            query = query,
            params = 문자_숫자_타입_파라미터,
            userId = "peter.park"
        )

        assertThat(key).isEqualTo("harrods:peter.park:${query.hashCode()}:name:peter:age:10")
    }

    @Test
    fun `리스트 파라미터로 키를 생성한다`() {
        val query = "SELECT * FROM user WHERE name IN ('peter','woo')"
        val 리스트_타입_파라미터 = mapOf("name" to listOf("peter", "woo"))

        val key = keyGenerator.generate(
            query = query,
            params = 리스트_타입_파라미터,
            userId = "peter.park"
        )

        assertThat(key).isEqualTo("harrods:peter.park:${query.hashCode()}:name:peter,woo")
    }

    @Test
    fun `지원하지 않는 파라미터는 에러를 발생한다`() {
        val 지원하지_않는_파라미터_타입 = mapOf("name" to "peter")

        val result = assertThrows<CodeAwareException> {
            keyGenerator.generate(
                query = "SELECT * FROM user WHERE name = ",
                params = mapOf("name" to 지원하지_않는_파라미터_타입),
                userId = "peter.park"
            )
        }

        assertThat(result.error).isEqualTo(ErrorCode.UNSUPPORTED_PARAMETER_TYPE)
    }
}
