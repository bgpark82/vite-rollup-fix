package com.musinsa.harrods.query.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class KeyGeneratorTest {

    @Test
    fun `키를 생성한다`() {
        val keyGenerator = KeyGenerator()
        val query = "SELECT * FROM user WHERE name = peter and age = 10"
        val params = mapOf("name" to "peter", "age" to "10")

        val key = keyGenerator.generate(query, params)

        assertThat(key).isEqualTo("harrods:${query.hashCode()}:name:peter:age:10")
    }
}
