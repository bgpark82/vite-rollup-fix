package com.musinsa.stat.sales.service

import com.musinsa.stat.sales.domain.AdRowMapper
import com.musinsa.stat.sales.domain.BrandPartnerRowMapper
import com.musinsa.stat.sales.domain.BrandRowMapper
import com.musinsa.stat.sales.domain.CouponRowMapper
import com.musinsa.stat.sales.domain.DailyAndMontlyRowMapper
import com.musinsa.stat.sales.domain.GoodsRowMapper
import com.musinsa.stat.sales.domain.Metric
import com.musinsa.stat.sales.domain.PartnerRowMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

private class RowMapperFactoryTest {

    @Test
    fun 일별매출통계_ROW_MAPPER_를_가져온다() {
        assertThat(RowMapperFactory.getRowMapper(Metric.DAILY)).isInstanceOf(
            DailyAndMontlyRowMapper::class.java
        )
    }

    @Test
    fun 월별매출통계_ROW_MAPPER_를_가져온다() {
        assertThat(RowMapperFactory.getRowMapper(Metric.MONTLY)).isInstanceOf(
            DailyAndMontlyRowMapper::class.java
        )
    }

    @Test
    fun 업체별매출통계_ROW_MAPPER_를_가져온다() {
        assertThat(RowMapperFactory.getRowMapper(Metric.PARTNER)).isInstanceOf(
            PartnerRowMapper::class.java
        )
    }

    @Test
    fun 브랜드별매출통계_ROW_MAPPER_를_가져온다() {
        assertThat(RowMapperFactory.getRowMapper(Metric.BRAND)).isInstanceOf(
            BrandRowMapper::class.java
        )
    }

    @Test
    fun 브랜드업체별매출통계_ROW_MAPPER_를_가져온다() {
        assertThat(RowMapperFactory.getRowMapper(Metric.BRAND_PARTNER)).isInstanceOf(
            BrandPartnerRowMapper::class.java
        )
    }

    @Test
    fun 상품별매출통계_ROW_MAPPER_를_가져온다() {
        assertThat(RowMapperFactory.getRowMapper(Metric.GOODS)).isInstanceOf(
            GoodsRowMapper::class.java
        )
    }

    @Test
    fun 광고별매출통계_ROW_MAPPER_를_가져온다() {
        assertThat(RowMapperFactory.getRowMapper(Metric.AD)).isInstanceOf(
            AdRowMapper::class.java
        )
    }

    @Test
    fun 쿠폰별매출통계_ROW_MAPPER_를_가져온다() {
        assertThat(RowMapperFactory.getRowMapper(Metric.COUPON)).isInstanceOf(
            CouponRowMapper::class.java
        )
    }
}
