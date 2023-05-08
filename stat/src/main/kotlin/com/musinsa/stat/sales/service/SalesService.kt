package com.musinsa.stat.sales.service

import com.musinsa.stat.databricks.service.DatabricksClient
import com.musinsa.stat.sales.config.QueryStore
import com.musinsa.stat.sales.domain.SalesStart
import com.musinsa.stat.sales.domain.SalesStatisticsRowMapper
import com.musinsa.stat.sales.dto.SalesStatistics
import com.musinsa.stat.sales.service.QueryGenerator.generate
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
    /**
     * 일별 매출통계를 가져온다.
     *
     * @param startDate 시작날짜
     * @param endDate 종료날짜
     * @param tag 태그
     * @param salesStart 매출시점
     * @param partnerId 업체
     * @param category
     * @param styleNumber 스타일넘버
     * @param goodsNumber 상품코드
     * @param brandId 브랜드
     * @param couponNumber 쿠폰
     * @param adCode 광고코드
     * @param specialtyCode 전문관코드
     * @param mdId 담당MD
     *
     * @return 매출통계 지표
     *
     */
    fun daily(
        startDate: String,
        endDate: String,
        tag: List<String>,
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
        val rs = jdbcTemplate.query(
            generate(
                query = databricksClient.getDatabricksQuery(queryStore.daily),
                startDate,
                endDate,
                tag,
                salesStart,
                partnerId,
                category,
                styleNumber,
                goodsNumber,
                brandId,
                couponNumber,
                adCode,
                specialtyCode,
                mdId
            ), SalesStatisticsRowMapper
        )
        println(rs)
        return emptyList()
    }


}