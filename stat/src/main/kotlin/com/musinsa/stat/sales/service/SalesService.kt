package com.musinsa.stat.sales.service

import com.musinsa.stat.sales.dto.SalesStatistics
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service

@Service
class SalesService(
    @Qualifier("databricksJdbcTemplate") val jdbcTemplate: JdbcTemplate
) {
    fun daily(tag: List<String>, startDate: String, endDate: String):
            List<SalesStatistics> {
        return emptyList()
    }
}