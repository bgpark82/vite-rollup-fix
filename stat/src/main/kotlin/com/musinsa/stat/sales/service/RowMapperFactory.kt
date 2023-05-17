package com.musinsa.stat.sales.service

import com.musinsa.stat.sales.domain.DailyAndMontlyRowMapper
import com.musinsa.stat.sales.domain.Metric
import com.musinsa.stat.sales.domain.Metric.*
import com.musinsa.stat.sales.domain.PartnerRowMapper
import com.musinsa.stat.sales.dto.SalesStatisticsMetric
import org.springframework.jdbc.core.RowMapper

/**
 * 매출통계에서 사용되는 RowMapper를 가져온다.
 */
class RowMapperFactory private constructor() {
    companion object {
        fun getRowMapper(metric: Metric): RowMapper<out SalesStatisticsMetric> {
            return when (metric) {
                DAILY -> DailyAndMontlyRowMapper
                MONTLY -> DailyAndMontlyRowMapper
                PARTNER -> PartnerRowMapper
                BRAND -> TODO()
                BRAND_PARTNER -> TODO()
                GOODS -> TODO()
                AD -> TODO()
                COUPON -> TODO()
                CATEGORY -> TODO()
                // TODO 에러 정의
                else -> throw Exception("존재하지 않는 Metric")
            }
        }
    }
}