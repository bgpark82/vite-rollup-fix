package com.musinsa.stat.sales.controller

import com.musinsa.stat.sales.domain.Metric
import com.musinsa.stat.sales.domain.OrderBy
import com.musinsa.stat.sales.domain.SalesStart
import com.musinsa.stat.sales.dto.SalesStatisticsResponse
import com.musinsa.stat.sales.fixture.DailyFixture
import com.musinsa.stat.sales.service.SalesService
import com.musinsa.stat.util.GET
import com.musinsa.stat.util.buildMockMvc
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.context.WebApplicationContext
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@ExtendWith(RestDocumentationExtension::class)
@WebMvcTest(controllers = [SalesController::class])
internal class SalesControllerTest(@Autowired var mockMvc: MockMvc) {
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
    fun 일별매출통계_가져오기() {
        val 응답값 = SalesStatisticsResponse(
            listOf(
                DailyFixture.DAILY_20230505(),
                DailyFixture.DAILY_20230506()
            )
        )
        val 지표 = Metric.DAILY
        val 시작날짜 = "20230505"
        val 종료날짜 = "20230506"
        val 매출시점 = SalesStart.SHIPPING_REQUEST
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
                tag = null,
                salesStart = 매출시점,
                partnerId = null,
                category = null,
                styleNumber = null,
                goodsNumber = null,
                brandId = null,
                couponNumber = null,
                adCode = null,
                specialtyCode = null,
                mdId = null,
                orderBy = 정렬키
            )
        ).thenReturn(응답값)

        var queryParams = LinkedMultiValueMap<String, String>()
        queryParams["startDate"] = 시작날짜
        queryParams["endDate"] = 종료날짜
        queryParams["salesStart"] = 매출시점.name
        queryParams["orderBy"] = 정렬키.toString()

        mockMvc.GET("/sales-statistics/".plus(지표), queryParams)
            .andExpect(status().isOk)
            .andDo(MockMvcResultHandlers.print())
            .andDo(document("sales-statistics"))
    }
}