package com.musinsa.stat.sales.service

import com.musinsa.stat.sales.domain.SalesStart
import com.musinsa.stat.sales.dto.SalesStatistics
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service

@Service
class SalesService(
    @Qualifier("databricksJdbcTemplate") val jdbcTemplate: JdbcTemplate
) {
    // TODO 필수값 체크
    // TODO Javadoc 추가
    fun daily(
        tag: List<String>,
        startDate: String,
        endDate: String,
        salesStart: SalesStart,
        partnerId: String,
        category: String,
        styleNumber: String,
        goodsNumber: Long,
        brandId: String,
        couponNumber: String,
        adCode: String,
        specialtyCode: String,
        mdId: String
    ): List<SalesStatistics> {
        return emptyList()
    }
}