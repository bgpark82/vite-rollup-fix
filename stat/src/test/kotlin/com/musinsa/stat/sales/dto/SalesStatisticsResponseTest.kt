package com.musinsa.stat.sales.dto

import com.musinsa.stat.sales.fixture.DailyFixture.DAILY_20230505
import com.musinsa.stat.sales.fixture.DailyFixture.DAILY_20230506
import com.musinsa.stat.sales.fixture.DailyFixture.DAILY_SUM
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

private class SalesStatisticsResponseTest {
    @Test
    fun 응답값을_조립한다() {
        // given
        val 테스트를_위한_DAILY_LIST =
            listOf(
                DAILY_SUM(),
                DAILY_20230505(),
                DAILY_20230506()
            )

        // when
        val 결과값 = SalesStatisticsResponse(테스트를_위한_DAILY_LIST)

        // then
        assertAll(
            { assertThat(결과값.sum.isGrouping).isTrue() },
            { assertThat(결과값.content[0].isGrouping).isFalse() },
            { assertThat(결과값.content[1].isGrouping).isFalse() }
        )
    }
}