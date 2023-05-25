package com.musinsa.stat.sales.service

import com.musinsa.stat.databricks.service.DatabricksClient
import com.musinsa.stat.error.CodeAwareException
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
import org.mockito.ArgumentMatchers.anyString
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.springframework.jdbc.core.JdbcTemplate
import java.time.LocalDate

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
            startDate = LocalDate.now(),
            endDate = LocalDate.now().plusMonths(1),
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

    @Test
    fun 조회기간이_1년을_넘을경우_예외처리() {
        // given
        val 시작날짜 = LocalDate.now()
        val 시작날짜_ADD_1_YEAR = 시작날짜.plusYears(1)

        // when
        val 에러 = assertThrows<CodeAwareException> {
            salesService.getSalesStatistics(
                metric = Metric.DAILY,
                startDate = 시작날짜,
                endDate = 시작날짜_ADD_1_YEAR,
                salesStart = SalesStart.SHIPPING_REQUEST,
                orderBy = OrderBy.date
            )
        }

        // then
        assertThat(에러.error).isEqualTo(SalesError.NON_VALID_DATE)
    }

    @Test
    fun 조회종료시점이_시작시점보다_클_경우_예외처리() {
        // given
        val 시작날짜 = LocalDate.now()
        val 시작날짜_MINUS_1_DAY = 시작날짜.minusDays(1)

        // when
        val 에러 = assertThrows<CodeAwareException> {
            salesService.getSalesStatistics(
                metric = Metric.DAILY,
                startDate = 시작날짜,
                endDate = 시작날짜_MINUS_1_DAY,
                salesStart = SalesStart.SHIPPING_REQUEST,
                orderBy = OrderBy.date
            )
        }

        // then
        assertThat(에러.error).isEqualTo(SalesError.NON_VALID_DATE)
    }
}