package com.musinsa.stat.sales.service

import com.musinsa.stat.databricks.service.DatabricksClient
import com.musinsa.stat.sales.config.QueryStore
import com.musinsa.stat.sales.domain.SalesStart
import com.musinsa.stat.sales.domain.SalesStatisticsRowMapper
import com.musinsa.stat.sales.dto.SalesStatisticsResponse
import com.musinsa.stat.sales.fixture.DailyFixture.DAILY_20230505
import com.musinsa.stat.sales.fixture.DailyFixture.DAILY_20230506
import com.musinsa.stat.sales.fixture.DailyFixture.DAILY_SUM
import com.musinsa.stat.sales.fixture.Query.SAMPLE_QUERY
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.ArgumentMatchers.eq
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.springframework.jdbc.core.JdbcTemplate

private class SalesServiceTest {
    private val jdbcTemplate: JdbcTemplate = mock()
    private lateinit var queryStore: QueryStore
    private val databricksClient: DatabricksClient = mock()
    private lateinit var salesService: SalesService

    @BeforeEach
    fun setUp() {
        queryStore = QueryStore(daily = "daily")
        salesService = SalesService(jdbcTemplate, queryStore, databricksClient)
    }

    @Test
    fun 일별_매출통계를_가져온다() {
        // given
        whenever(
            jdbcTemplate.query(
                anyString(),
                eq(SalesStatisticsRowMapper)
            )
        ).thenReturn(listOf(DAILY_SUM, DAILY_20230505, DAILY_20230506))
        whenever(databricksClient.getDatabricksQuery(anyString())).thenReturn(
            SAMPLE_QUERY
        )

        // when
        val 결과값 = salesService.daily(
            startDate = "startDate",
            endDate = "endDate",
            salesStart = SalesStart.SHIPPING_REQUEST,
            orderBy = "date"
        )

        // then
        assertThat(결과값).isEqualTo(
            SalesStatisticsResponse(
                sum = DAILY_SUM,
                content = listOf(DAILY_20230505, DAILY_20230506)
            )
        )
    }
}
