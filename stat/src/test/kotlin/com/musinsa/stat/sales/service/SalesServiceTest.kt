package com.musinsa.stat.sales.service

import com.musinsa.stat.databricks.service.DatabricksClient
import com.musinsa.stat.sales.config.QueryStore
import com.musinsa.stat.sales.domain.DailyAndMontlyRowMapper
import com.musinsa.stat.sales.domain.Metric
import com.musinsa.stat.sales.domain.SalesStart
import com.musinsa.stat.sales.dto.SalesStatisticsResponse
import com.musinsa.stat.sales.fixture.DailyFixture.DAILY_20230505
import com.musinsa.stat.sales.fixture.DailyFixture.DAILY_20230506
import com.musinsa.stat.sales.fixture.DailyFixture.DAILY_SUM
import com.musinsa.stat.sales.fixture.Query.SAMPLE_QUERY
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.mockito.ArgumentMatchers.anyString
import org.mockito.kotlin.eq
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
        queryStore = QueryStore(
            daily = "필수값이라 의미 없이 추가",
            montly = "필수값이라 의미 없이 추가",
            partner = "필수값이라 의미 없이 추가",
            brand = "필수값이라 의미 없이 추가",
            brandPartner = "필수값이라 의미 없이 추가",
            goods = "필수값이라 의미 없이 추가",
            ad = "필수값이라 의미 없이 추가",
            coupon = "필수값이라 의미 없이 추가",
            category = "필수값이라 의미 없이 추가"
        )
        salesService = SalesService(jdbcTemplate, queryStore, databricksClient)
    }

    @Test
    fun 매출통계를_가져온다() {
        // given
        val 테스트를_위한_DAILY_LIST =
            listOf(DAILY_SUM(), DAILY_20230505(), DAILY_20230506())
        whenever(
            jdbcTemplate.query(
                anyString(),
                eq(DailyAndMontlyRowMapper)
            )
        ).thenReturn(테스트를_위한_DAILY_LIST)
        whenever(databricksClient.getDatabricksQuery(anyString())).thenReturn(
            SAMPLE_QUERY
        )

        // when
        val 결과값 = salesService.getSalesStatistics(
            metric = Metric.DAILY,
            startDate = "필수값이라 의미 없이 추가",
            endDate = "필수값이라 의미 없이 추가",
            salesStart = SalesStart.SHIPPING_REQUEST,
            orderBy = "필수값이라 의미 없이 추가"
        )

        // then
        val 기댓값 = SalesStatisticsResponse(테스트를_위한_DAILY_LIST)
        assertAll(
            { assertThat(결과값.sum.toString()).isEqualTo(기댓값.sum.toString()) },
            { assertThat(결과값.content.toString()).isEqualTo(기댓값.content.toString()) }
        )
    }
}