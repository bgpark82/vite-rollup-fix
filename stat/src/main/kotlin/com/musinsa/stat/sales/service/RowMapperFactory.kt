package com.musinsa.stat.sales.service

import com.musinsa.stat.sales.domain.AdRowMapper
import com.musinsa.stat.sales.domain.BrandPartnerRowMapper
import com.musinsa.stat.sales.domain.BrandRowMapper
import com.musinsa.stat.sales.domain.CategoryRowMapper
import com.musinsa.stat.sales.domain.CouponRowMapper
import com.musinsa.stat.sales.domain.DailyAndMontlyRowMapper
import com.musinsa.stat.sales.domain.GoodsRowMapper
import com.musinsa.stat.sales.domain.Metric
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
                Metric.DAILY -> DailyAndMontlyRowMapper
                Metric.MONTLY -> DailyAndMontlyRowMapper
                Metric.PARTNER -> PartnerRowMapper
                Metric.BRAND -> BrandRowMapper
                Metric.BRAND_PARTNER -> BrandPartnerRowMapper
                Metric.GOODS -> GoodsRowMapper
                Metric.AD -> AdRowMapper
                Metric.COUPON -> CouponRowMapper
                Metric.CATEGORY -> CategoryRowMapper
            }
        }
    }
}
