package com.musinsa.stat.sales.service

import com.musinsa.stat.databricks.service.DatabricksClient
import com.musinsa.stat.sales.config.QueryStore
import com.musinsa.stat.sales.domain.SalesStart
import com.musinsa.stat.sales.domain.SalesStatisticsRowMapper
import com.musinsa.stat.sales.dto.Daily
import com.musinsa.stat.sales.dto.SalesStatisticsResponse
import com.musinsa.stat.sales.service.QueryGenerator.generate
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service

@Service
class SalesService(
    @Qualifier("databricksJdbcTemplate") private val jdbcTemplate: JdbcTemplate,
    private val queryStore: QueryStore,
    private val databricksClient: DatabricksClient
) {
    /**
     * 일별 매출통계를 가져온다.
     *
     * @param startDate 시작날짜
     * @param endDate 종료날짜
     * @param tag 태그. 기본값: 빈배열
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
     * @param orderBy 정렬키
     * @param size 페이징 시 가져올 원소 수
     * @param number 가져올 페이지
     *
     * @return 매출통계 지표
     *
     */
    fun daily(
        startDate: String,
        endDate: String,
        tag: List<String>? = emptyList(),
        salesStart: SalesStart,
        partnerId: String? = String(),
        category: String? = String(),
        styleNumber: String? = String(),
        goodsNumber: String? = String(),
        brandId: String? = String(),
        couponNumber: String? = String(),
        adCode: String? = String(),
        specialtyCode: String? = String(),
        mdId: String? = String(),
        orderBy: String
    ): SalesStatisticsResponse<Daily> {
        val queryResult = jdbcTemplate.query(
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
                mdId,
                orderBy
            ), SalesStatisticsRowMapper
        )

        return SalesStatisticsResponse(queryResult)
    }
}