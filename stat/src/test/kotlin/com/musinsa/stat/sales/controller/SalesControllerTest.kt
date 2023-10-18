package com.musinsa.stat.sales.controller

import com.musinsa.common.restdoc.DOCS_생성
import com.musinsa.common.restdoc.ENUM_LINK_DOCS_BUILDER
import com.musinsa.common.restdoc.GET
import com.musinsa.common.restdoc.RestDocsControllerHelper
import com.musinsa.common.restdoc.성공_검증
import com.musinsa.common.util.ObjectMapperFactory.writeValueAsString
import com.musinsa.stat.restdoc.METRIC_DOCUMENT_URL
import com.musinsa.stat.sales.domain.Metric
import com.musinsa.stat.sales.domain.OrderBy
import com.musinsa.stat.sales.domain.OrderDirection
import com.musinsa.stat.sales.domain.SalesStart
import com.musinsa.stat.sales.dto.SalesStatisticsResponse
import com.musinsa.stat.sales.dto.매출통계_명세
import com.musinsa.stat.sales.dto.일별_월별_명세
import com.musinsa.stat.sales.fixture.DailyFixture
import com.musinsa.stat.sales.service.SalesService
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.util.LinkedMultiValueMap
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@WebMvcTest(controllers = [SalesController::class])
private class SalesControllerTest : RestDocsControllerHelper() {
    @MockBean
    lateinit var salesService: SalesService

    val 페이지_사이즈: Long = 500
    val 페이지: Long = 0
    val 시작날짜 = "20230505"
    val 종료날짜 = "20230506"
    val 태그 = listOf("청바지", "반바지")
    val 매출시점 = SalesStart.SHIPPING_REQUEST
    val 업체 = listOf("musinsa")
    val 카테고리 = listOf("004002")
    val 스타일넘버 = listOf("DMMT73961-OW")
    val 상품코드 = listOf("174846")
    val 브랜드 = listOf("greentoys")
    val 쿠폰 = listOf("75559", "12345")
    val 광고코드 = listOf("NVSH")
    val 전문관코드 = listOf("beauty", "golf")
    val 담당MD = listOf("naka.da")
    val 정렬키 = OrderBy.Date
    val 정렬방향 = OrderDirection.ASC

    @Test
    fun 매출통계_가져오기() {
        val 지표 = Metric.DAILY
        val queryParams = 파라미터_설정(지표, 응답값_설정())

        mockMvc.GET("/sales-statistics/{metric}", 지표.name, queryParams)
            .성공_검증(writeValueAsString(응답값_설정()))
            .DOCS_생성(
                "sales-statistics",
                listOf(
                    ENUM_LINK_DOCS_BUILDER(
                        "metric",
                        METRIC_DOCUMENT_URL,
                        "매출통계유형"
                    )
                ),
                매출통계_조회_요청값_명세(),
                매출통계_명세(일별_월별_명세())
            )
    }

    @Test
    fun `상품별 매출통계는 별도의 메소드를 호출한다`() {
        val 지표 = Metric.GOODS
        val queryParams = 파라미터_설정(지표, 응답값_설정())

        mockMvc.GET("/sales-statistics/{metric}", 지표.name, queryParams)

        verify(salesService, times(1)).getGoodsSalesStatistics(
            metric = 지표,
            startDate = LocalDate.parse(
                시작날짜,
                DateTimeFormatter.ofPattern("yyyyMMdd")
            ),
            endDate = LocalDate.parse(
                종료날짜,
                DateTimeFormatter.ofPattern("yyyyMMdd")
            ),
            tag = 태그,
            salesStart = 매출시점,
            partnerId = 업체,
            category = 카테고리,
            styleNumber = 스타일넘버,
            goodsNumber = 상품코드,
            brandId = 브랜드,
            couponNumber = 쿠폰,
            adCode = 광고코드,
            specialtyCode = 전문관코드,
            mdId = 담당MD,
            orderBy = 정렬키,
            orderDirection = 정렬방향,
            pageSize = 페이지_사이즈,
            page = 페이지
        )
    }

    @ParameterizedTest
    @EnumSource(mode = EnumSource.Mode.EXCLUDE, names = ["GOODS"])
    fun `상품별을 제외한 매출통계는 기본 메소드를 호출한다`(metric: Metric) {
        val 지표 = metric
        val queryParams = 파라미터_설정(지표, 응답값_설정())

        mockMvc.GET("/sales-statistics/{metric}", 지표.name, queryParams)

        verify(salesService, times(1)).getSalesStatistics(
            metric = 지표,
            startDate = LocalDate.parse(
                시작날짜,
                DateTimeFormatter.ofPattern("yyyyMMdd")
            ),
            endDate = LocalDate.parse(
                종료날짜,
                DateTimeFormatter.ofPattern("yyyyMMdd")
            ),
            tag = 태그,
            salesStart = 매출시점,
            partnerId = 업체,
            category = 카테고리,
            styleNumber = 스타일넘버,
            goodsNumber = 상품코드,
            brandId = 브랜드,
            couponNumber = 쿠폰,
            adCode = 광고코드,
            specialtyCode = 전문관코드,
            mdId = 담당MD,
            orderBy = 정렬키,
            orderDirection = 정렬방향,
            pageSize = 페이지_사이즈,
            page = 페이지
        )
    }

    private fun 파라미터_설정(
        지표: Metric,
        응답값: SalesStatisticsResponse
    ): LinkedMultiValueMap<String, String> {
        whenever(
            salesService.getSalesStatistics(
                metric = 지표,
                startDate = LocalDate.parse(
                    시작날짜,
                    DateTimeFormatter.ofPattern("yyyyMMdd")
                ),
                endDate = LocalDate.parse(
                    종료날짜,
                    DateTimeFormatter.ofPattern("yyyyMMdd")
                ),
                tag = 태그,
                salesStart = 매출시점,
                partnerId = 업체,
                category = 카테고리,
                styleNumber = 스타일넘버,
                goodsNumber = 상품코드,
                brandId = 브랜드,
                couponNumber = 쿠폰,
                adCode = 광고코드,
                specialtyCode = 전문관코드,
                mdId = 담당MD,
                orderBy = 정렬키,
                orderDirection = 정렬방향,
                pageSize = 페이지_사이즈,
                page = 페이지
            )
        ).thenReturn(응답값)

        val queryParams = LinkedMultiValueMap<String, String>()
        queryParams["startDate"] = 시작날짜
        queryParams["endDate"] = 종료날짜
        queryParams["tag"] = 태그
        queryParams["salesStart"] = 매출시점.name
        queryParams["partnerId"] = 업체
        queryParams["category"] = 카테고리
        queryParams["styleNumber"] = 스타일넘버
        queryParams["goodsNumber"] = 상품코드
        queryParams["brandId"] = 브랜드
        queryParams["couponNumber"] = 쿠폰
        queryParams["adCode"] = 광고코드
        queryParams["specialtyCode"] = 전문관코드
        queryParams["mdId"] = 담당MD
        queryParams["orderBy"] = 정렬키.toString()
        queryParams["orderDirection"] = 정렬방향.toString()
        queryParams["pageSize"] = 페이지_사이즈.toString()
        queryParams["page"] = 페이지.toString()

        return queryParams
    }

    private fun 응답값_설정(): SalesStatisticsResponse {
        val 페이지_사이즈: Long = 500
        val 페이지: Long = 0
        return SalesStatisticsResponse(
            listOf(
                DailyFixture.DAILY_20230505(),
                DailyFixture.DAILY_20230506()
            ),
            페이지_사이즈,
            페이지,
            "실행된 SQL"
        )
    }
}
