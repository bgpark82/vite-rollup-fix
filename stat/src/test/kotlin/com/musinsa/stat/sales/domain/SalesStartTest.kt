package com.musinsa.stat.sales.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

private class SalesStartTest {
    @Test
    fun 매출시점은_2개_존재한다() {
        assertThat(SalesStart.values().size).isEqualTo(2)
    }

    @ParameterizedTest
    @EnumSource(mode = EnumSource.Mode.MATCH_ALL)
    fun 존재하는_매출시점_확인(salesStart: SalesStart) {
        assertThat(salesStart).isIn(
            SalesStart.PURCHASE_CONFIRM,
            SalesStart.SHIPPING_REQUEST
        )
        assertThat(salesStart.description).isIn(
            "출고요청", "구매확정"
        )
    }
}