package com.musinsa.harrods.query.service

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class TemplateFormatterTest {

    private val templateFormatter = TemplateFormatter()

    @Test
    fun `중괄호 사이의 공간을 제거한다`() {
        val query = "SELECT * FROM user WHERE name = {{ name}} and age = {{age }} and gender = {{ gender }} and date = {{date   }}"

        val template = templateFormatter.format(query)

        Assertions.assertThat(template).isEqualTo("SELECT * FROM user WHERE name = {{name}} and age = {{age}} and gender = {{gender}} and date = {{date}}")
    }

    @Test
    fun `중괄호가 없으면 그대로 반환한다`() {
        val query = "SELECT * FROM user"

        val template = templateFormatter.format(query)

        Assertions.assertThat(template).isEqualTo("SELECT * FROM user")
    }
}
