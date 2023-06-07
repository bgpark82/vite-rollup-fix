package com.musinsa.stat.sales.controller

import com.musinsa.stat.restdoc.DOCS_생성
import com.musinsa.stat.restdoc.GET
import com.musinsa.stat.restdoc.buildMockMvc
import com.musinsa.stat.restdoc.성공_검증
import com.musinsa.stat.sales.domain.Metric
import com.musinsa.stat.sales.domain.OrderBy
import com.musinsa.stat.sales.domain.SalesStart
import com.musinsa.stat.sales.dto.SalesStatisticsResponse
import com.musinsa.stat.sales.dto.매출통계_명세
import com.musinsa.stat.sales.dto.일별_월별_명세
import com.musinsa.stat.sales.fixture.DailyFixture
import com.musinsa.stat.sales.service.SalesService
import com.musinsa.stat.util.ObjectMapperFactory.writeValueAsString
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import org.springframework.test.web.servlet.MockMvc
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.context.WebApplicationContext
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@ExtendWith(RestDocumentationExtension::class)
@WebMvcTest(controllers = [SalesController::class])
private class SalesControllerTest(@Autowired var mockMvc: MockMvc) {
    @MockBean
    lateinit var salesService: SalesService

    @BeforeEach
    fun setUp(
        webApplicationContext: WebApplicationContext,
        restDocumentationContextProvider: RestDocumentationContextProvider
    ) {
        this.mockMvc = buildMockMvc(
            webApplicationContext,
            restDocumentationContextProvider
        )
    }

    @Test
    fun 매출통계_가져오기() {
        val 응답값 = SalesStatisticsResponse(
            listOf(
                DailyFixture.DAILY_20230505(),
                DailyFixture.DAILY_20230506()
            )
        )
        val 지표 = Metric.DAILY
        val 시작날짜 = "20230505"
        val 종료날짜 = "20230506"
        val 태그 = listOf("청바지", "반바지")
        val 매출시점 = SalesStart.SHIPPING_REQUEST
        val 업체 = "musinsa"
        val 카테고리 = "004002"
        val 스타일넘버 = "DMMT73961-OW"
        val 상품코드 = "174846"
        val 브랜드 = "greentoys"
        val 쿠폰 = "75559"
        val 광고코드 = "NVSH"
        val 전문관코드 = "beauty"
        val 담당MD = "naka.da"
        val 정렬키 = OrderBy.date

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
                orderBy = 정렬키
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

        // TODO sum, average 중복 삭제
        mockMvc.GET("/sales-statistics/{metric}", 지표.name, queryParams)
            .성공_검증(writeValueAsString(응답값))
            .DOCS_생성(
                "sales-statistics",
                listOf(
                    parameterWithName("metric")
                        // TODO 깔끔하게 제공
                        .description(
                            "지표: \n" +
                                    "    DAILY(\"일별\"),\n" +
                                    "    MONTLY(\"월별\"),\n" +
                                    "    PARTNER(\"업체별\"),\n" +
                                    "    BRAND(\"브랜드별\"),\n" +
                                    "    BRAND_PARTNER(\"브랜드업체별\"),\n" +
                                    "    GOODS(\"상품별\"),\n" +
                                    "    AD(\"광고별\"),\n" +
                                    "    COUPON(\"쿠폰별\"),\n" +
                                    "    CATEGORY(\"카테고리별\")"
                        )
                ),
                매출통계_조회_요청값_명세(),
                매출통계_명세(일별_월별_명세())
            )
    }

    // TODO 에러테스트 추가
    // 필수값 누락
    fun errorTest() {

    }
}