package com.musinsa.stat.sales.dto

import com.musinsa.stat.sales.fixture.DailyFixture
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

private class SalesStatisticsResponseTest {
    @Test
    fun 응답값을_조립한다() {
        // given
        val 테스트를_위한_DAILY_LIST =
            listOf(
                DailyFixture.DAILY_SUM,
                DailyFixture.DAILY_20230505,
                DailyFixture.DAILY_20230506
            )

        // when
        val 결과값 = SalesStatisticsResponse<Daily>(테스트를_위한_DAILY_LIST)

        // then
        assertAll(
            { Assertions.assertThat(결과값.sum.isGrouping).isTrue() },
            { Assertions.assertThat(결과값.content[0].isGrouping).isFalse() },
            { Assertions.assertThat(결과값.content[1].isGrouping).isFalse() }
        )
    }
}