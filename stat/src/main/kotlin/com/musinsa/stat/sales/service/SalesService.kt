package com.musinsa.stat.sales.service

import com.musinsa.stat.databricks.service.DatabricksClient
import com.musinsa.stat.sales.config.QueryStore
import com.musinsa.stat.sales.domain.SalesStatisticsRowMapper
import com.musinsa.stat.sales.dto.SalesStatistics
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service

@Service
class SalesService(
    @Qualifier("databricksJdbcTemplate") val jdbcTemplate: JdbcTemplate,
    val queryStore: QueryStore,
    val databricksClient: DatabricksClient
) {
    // TODO 필수값 체크
    // TODO Javadoc 추가
    fun daily(
//        startDate: String,
//        endDate: String,
//        tag: List<String>,
//        salesStart: SalesStart,
//        partnerId: String,
//        category: String,
//        styleNumber: String,
//        goodsNumber: Long,
//        brandId: String,
//        couponNumber: String,
//        adCode: String,
//        specialtyCode: String,
//        mdId: String
    ): List<SalesStatistics> {
//        val testQuery = Query().daily
        val query = databricksClient.getDatabricksQuery(queryStore.daily)
        println(query)
        val rs = jdbcTemplate.query(query, SalesStatisticsRowMapper)
        println(rs)
        return emptyList()
    }


}