package com.musinsa.stat.sales.service

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
//        salesService.daily()
    }
}