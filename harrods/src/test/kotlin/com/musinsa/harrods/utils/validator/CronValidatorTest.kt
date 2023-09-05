package com.musinsa.harrods.utils.validator

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class CronValidatorTest {

    private val validator = CronValidator()

    @ParameterizedTest
    @ValueSource(strings = ["* * * *", "* * * * * *"])
    fun `유효하지 않은 cron 표현식`(유효하지_않은_cron: String) {
        assertThat(validator.isValid(유효하지_않은_cron, null)).isFalse()
    }

    @Test
    fun `매일 자정(0시 0분)에 실행`() {
        assertThat(validator.isValid("0 0 * * *", null)).isTrue()
    }

    @Test
    fun `매 20분마다 실행`() {
        assertThat(validator.isValid("*/20 * * * *", null)).isTrue()
    }

    @Test
    fun `매일 오전 9시, 오후 12시, 오후 3시 정각에 실행`() {
        assertThat(validator.isValid("0 9,12,15 * * *", null)).isTrue()
    }

    @Test
    fun `매일 오전 5시부터 오후 8시까지 10분 간격으로 실행`() {
        assertThat(validator.isValid("*/10 5-20 * * *", null)).isTrue()
    }

    @Test
    fun `매주 토요일과 일요일 오후 2시부터 5시까지 10분 간격으로 실행`() {
        assertThat(validator.isValid("*/10 14-17 * * 6,7", null)).isTrue()
    }
}
