package com.musinsa.stat.sales.service

import com.musinsa.stat.databricks.service.DatabricksClient
import com.musinsa.stat.error.IntentionalRuntimeException
import com.musinsa.stat.sales.config.QueryStore
import com.musinsa.stat.sales.domain.DailyAndMontlyRowMapper
import com.musinsa.stat.sales.domain.Metric
import com.musinsa.stat.sales.domain.OrderBy
import com.musinsa.stat.sales.domain.SalesStart
import com.musinsa.stat.sales.dto.SalesStatisticsResponse
import com.musinsa.stat.sales.error.SalesError
import com.musinsa.stat.sales.fixture.DailyFixture.DAILY_20230505
import com.musinsa.stat.sales.fixture.DailyFixture.DAILY_20230506
import com.musinsa.stat.sales.fixture.Query.SAMPLE_QUERY
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
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
            listOf(DAILY_20230505(), DAILY_20230506())
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
            orderBy = OrderBy.date
        )

        // then
        val 기댓값 = SalesStatisticsResponse(테스트를_위한_DAILY_LIST)
        assertAll(
            { assertThat(결과값.sum).isNotNull },
            { assertThat(결과값.average).isNotNull },
            { assertThat(결과값.content.toString()).isEqualTo(기댓값.content.toString()) }
        )
    }

    @ParameterizedTest
    @CsvSource(
        value = ["2023051:20230510", "20230501:2023051", "202305:20230510", "20230501:202305"],
        delimiter = ':'
    )
    fun LOCALDATE_변수로_변환할수_없는경우_예외처리(startDate: String, endDate: String) {
        // when
        val 에러 = assertThrows<IntentionalRuntimeException> {
            salesService.getSalesStatistics(
                metric = Metric.DAILY,
                startDate = startDate,
                endDate = endDate,
                salesStart = SalesStart.SHIPPING_REQUEST,
                orderBy = OrderBy.date
            )
        }

        // then
        assertThat(에러.error).isEqualTo(SalesError.NON_VALID_DATE)
    }

    @Test
    fun 조회기간이_1년을_넘을경우_예외처리() {

    }

    @Test
    fun 조회종료시점이_시작시점보다_클_경우_예외처리() {

    }
}