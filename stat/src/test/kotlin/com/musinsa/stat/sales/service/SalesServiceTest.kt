package com.musinsa.stat.sales.service

import com.musinsa.common.databricks.service.StatDatabricksClient
import com.musinsa.common.error.CodeAwareException
import com.musinsa.stat.sales.config.QueryStore
import com.musinsa.stat.sales.domain.DailyAndMontlyRowMapper
import com.musinsa.stat.sales.domain.Metric
import com.musinsa.stat.sales.domain.OrderBy
import com.musinsa.stat.sales.domain.OrderDirection
import com.musinsa.stat.sales.domain.SalesFunnel
import com.musinsa.stat.sales.domain.SalesStart
import com.musinsa.stat.sales.dto.SalesStatisticsResponse
import com.musinsa.stat.sales.error.SalesError
import com.musinsa.stat.sales.fixture.DailyFixture.DAILY_20230505
import com.musinsa.stat.sales.fixture.DailyFixture.DAILY_20230506
import com.musinsa.stat.sales.fixture.QueryFixture.SAMPLE_QUERY
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import org.mockito.ArgumentMatchers.anyString
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.springframework.jdbc.core.JdbcTemplate
import java.time.LocalDate

private class SalesServiceTest {
    private val jdbcTemplate: JdbcTemplate = mock()
    private lateinit var queryStore: QueryStore
    private val statDatabricksClient: StatDatabricksClient = mock()
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
        salesService =
            SalesService(jdbcTemplate, queryStore, statDatabricksClient)
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
        whenever(statDatabricksClient.getDatabricksQuery(anyString())).thenReturn(
            SAMPLE_QUERY
        )

        // when
        val 결과값 = salesService.getSalesStatistics(
            metric = Metric.DAILY,
            startDate = LocalDate.now(),
            endDate = LocalDate.now().plusMonths(1),
            salesStart = SalesStart.SHIPPING_REQUEST,
            salesFunnel = SalesFunnel.DEFAULT,
            orderBy = OrderBy.Date,
            orderDirection = OrderDirection.ASC,
            pageSize = 100,
            page = 1
        )

        // then
        val 기댓값 = SalesStatisticsResponse(테스트를_위한_DAILY_LIST, 100, 1, "SQL")
        assertAll(
            { assertThat(결과값.sum).isNotNull },
            { assertThat(결과값.average).isNotNull },
            { assertThat(결과값.content.toString()).isEqualTo(기댓값.content.toString()) }
        )
    }

    @ParameterizedTest
    @EnumSource(mode = EnumSource.Mode.MATCH_ALL)
    fun 조회기간이_1년을_넘을경우_예외처리(metric: Metric) {
        // given
        val 시작날짜 = LocalDate.now()
        val 시작날짜_ADD_1_YEAR = 시작날짜.plusYears(1)

        // when
        val 에러 = assertThrows<CodeAwareException> {
            salesService.getSalesStatistics(
                metric = metric,
                startDate = 시작날짜,
                endDate = 시작날짜_ADD_1_YEAR,
                salesStart = SalesStart.SHIPPING_REQUEST,
                salesFunnel = SalesFunnel.DEFAULT,
                orderBy = OrderBy.Date,
                orderDirection = OrderDirection.ASC,
                pageSize = 100,
                page = 1
            )
        }

        // then
        assertThat(에러.error).isEqualTo(SalesError.NON_VALID_DATE_PERIOD)
    }

    @ParameterizedTest
    @EnumSource(mode = EnumSource.Mode.MATCH_ALL)
    fun 조회종료시점이_시작시점보다_클_경우_예외처리(metric: Metric) {
        // given
        val 시작날짜 = LocalDate.now()
        val 시작날짜_MINUS_1_DAY = 시작날짜.minusDays(1)

        // when
        val 에러 = assertThrows<CodeAwareException> {
            salesService.getSalesStatistics(
                metric = metric,
                startDate = 시작날짜,
                endDate = 시작날짜_MINUS_1_DAY,
                salesStart = SalesStart.SHIPPING_REQUEST,
                salesFunnel = SalesFunnel.DEFAULT,
                orderBy = OrderBy.Date,
                orderDirection = OrderDirection.ASC,
                pageSize = 100,
                page = 1
            )
        }

        // then
        assertThat(에러.error).isEqualTo(SalesError.NON_VALID_DATE_PERIOD)
    }

    @Test
    fun 상품별_매출통계는_적어도_하나의_브랜드_업체코드_상품번호_담당MD_값이_없으면_예외_발생() {
        val 에러 = assertThrows<CodeAwareException> {
            salesService.getGoodsSalesStatistics(
                metric = Metric.GOODS,
                startDate = LocalDate.now(),
                endDate = LocalDate.now().plusMonths(1),
                salesStart = SalesStart.SHIPPING_REQUEST,
                orderBy = OrderBy.Date,
                orderDirection = OrderDirection.ASC,
                pageSize = 100,
                page = 1,
                brandId = emptyList(),
                partnerId = emptyList(),
                goodsNumber = emptyList(),
                mdId = emptyList(),
                salesFunnel = SalesFunnel.DEFAULT
            )
        }

        assertThat(에러.error).isEqualTo(SalesError.GOODS_STATISTICS_NEED_BRAND_PARTNER_GOODS_PARAMETERS)
    }

    @Test
    fun 월별_매출통계는_조회_시작날짜를_1일자로_지정한다() {
        // given
        val 해당월의_첫날이_아닌_조회_시작날짜 = LocalDate.of(2023, 6, 23)
        val 조회_종료날짜 = 해당월의_첫날이_아닌_조회_시작날짜.plusMonths(1)
        val 해당월의_첫날 =
            LocalDate.of(2023, 6, 1).toString().filterNot { it == '-' }
        // 아래 given 값들은 전혀 의미가 없으며 해당월의_첫날이_아닌_조회_시작날짜 의 SQL 리턴값만을 확인하기 위해 설정한다.
        val 테스트를_위한_DAILY_LIST =
            listOf(DAILY_20230505(), DAILY_20230506())
        whenever(
            jdbcTemplate.query(
                anyString(),
                eq(DailyAndMontlyRowMapper)
            )
        ).thenReturn(테스트를_위한_DAILY_LIST)
        whenever(statDatabricksClient.getDatabricksQuery(anyString())).thenReturn(
            SAMPLE_QUERY
        )

        // when
        val 결과값 = salesService.getSalesStatistics(
            metric = Metric.MONTLY,
            startDate = 해당월의_첫날이_아닌_조회_시작날짜,
            endDate = 조회_종료날짜,
            salesStart = SalesStart.SHIPPING_REQUEST,
            salesFunnel = SalesFunnel.DEFAULT,
            orderBy = OrderBy.Date,
            orderDirection = OrderDirection.ASC,
            pageSize = 100,
            page = 1
        ).sql

        // then
        assertThat(결과값.contains("om.ord_state_date >= '$해당월의_첫날'")).isTrue
    }
}
