package com.musinsa.stat.sales.config

import com.musinsa.stat.sales.domain.OrderBy
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class SalesWebConfigTest {
    @Test
    fun ORDER_BY_값으로_입력된_STRING_의_첫_글자를_대문자로_바꾼다() {
        val sut = StringToEnumConverter()

        val 결과값 = sut.convert("adCode")

        assertThat(결과값).isEqualTo(OrderBy.AdCode)
    }
}
