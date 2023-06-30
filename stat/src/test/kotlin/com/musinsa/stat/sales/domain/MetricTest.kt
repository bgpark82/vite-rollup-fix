package com.musinsa.stat.sales.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

private class MetricTest {
    @Test
    fun 매출통계유형은_9개_존재한다() {
        assertThat(Metric.values().size).isEqualTo(9)
    }

    @ParameterizedTest
    @EnumSource(mode = EnumSource.Mode.MATCH_ALL)
    fun 존재하는_매출통계유형_확인(metric: Metric) {
        assertThat(metric).isIn(
            Metric.DAILY,
            Metric.MONTLY,
            Metric.PARTNER,
            Metric.BRAND,
            Metric.BRAND_PARTNER,
            Metric.GOODS,
            Metric.AD,
            Metric.COUPON,
            Metric.CATEGORY
        )
        assertThat(metric.description).isIn(
            "일별",
            "월별",
            "업체별",
            "브랜드별",
            "브랜드업체별",
            "상품별",
            "광고별",
            "쿠폰별",
            "카테고리별"
        )
    }
}
