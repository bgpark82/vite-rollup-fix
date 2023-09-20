package com.musinsa.harrods.utils.validator

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class TemplateValidatorTest {

    private val validator = TemplateValidator()

    @ParameterizedTest
    @ValueSource(strings = ["SELECT", "select", "Select"])
    fun `SELECT 절에 *를 포함하지 않는다`(select: String) {
        assertThat(validator.isValid("$select *", null)).isFalse()
    }

    @Test
    fun `null을 포함하지 않는다`() {
        assertThat(validator.isValid(null, null)).isFalse()
    }
}
