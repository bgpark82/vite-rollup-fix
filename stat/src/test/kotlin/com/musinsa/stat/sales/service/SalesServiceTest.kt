package com.musinsa.stat.sales.service

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.jdbc.core.JdbcTemplate

internal class SalesServiceTest {

    lateinit var salesService: SalesService

    @MockBean
    val jdbcTemplate: JdbcTemplate = JdbcTemplate()

    @BeforeEach
    fun setUp() {
        salesService = SalesService(jdbcTemplate)
    }

    @Test
    fun 시작일자_종료일자_매출시점으로_일별_매출통계를_가져온다() {
        // given

        // when

        // then

    }

    init {

    }
}