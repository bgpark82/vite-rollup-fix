package com.musinsa.stat.sales.service

import com.musinsa.stat.sales.domain.SalesStart
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

// TEST 용도
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class SalesServiceIntegrationTest {
    @Autowired
    private lateinit var salesService: SalesService

    @Test
    fun 쿼리테스트() {
        val startDate = "20230401"
        val endDate = "20230430"
        val salesStart = SalesStart.SHIPPING_REQUEST
        val orderBy = "date"

        val result = salesService.daily(
            startDate = startDate,
            endDate = endDate,
            salesStart = salesStart,
            orderBy = orderBy,
        )

        println(result)
    }
}