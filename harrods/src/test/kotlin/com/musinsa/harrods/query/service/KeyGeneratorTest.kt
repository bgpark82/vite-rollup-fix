package com.musinsa.harrods.query.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class KeyGeneratorTest {

    private val keyGenerator = KeyGenerator()

    @Test
    fun `문자, 숫자 파라미터로 키를 생성한다`() {
        val query = "SELECT * FROM user WHERE name = 'peter' and age = 10"

        val key = keyGenerator.generate(
            query = query,
            userId = "peter.park"
        )

        assertThat(key).isEqualTo("harrods:peter.park:${query.hashCode()}")
    }

    @Test
    fun `리스트 파라미터로 키를 생성한다`() {
        val query = "SELECT * FROM user WHERE name IN ('peter','woo')"

        val key = keyGenerator.generate(
            query = query,
            userId = "peter.park"
        )

        assertThat(key).isEqualTo("harrods:peter.park:${query.hashCode()}")
    }
}
