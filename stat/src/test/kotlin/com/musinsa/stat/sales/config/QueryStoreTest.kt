package com.musinsa.stat.sales.config

import com.musinsa.stat.sales.domain.Metric
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

private class QueryStoreTest {
    private val queryStore = QueryStore(
        "daily",
        "montly",
        "partner",
        "brand",
        "brandPartner",
        "goods",
        "ad",
        "coupon",
        "category"
    )

    @ParameterizedTest
    @EnumSource(mode = EnumSource.Mode.MATCH_ALL)
    fun METRIC에_알맞는_QUERY_ID를_가져온다(metric: Metric) {
        if (metric != Metric.BRAND_PARTNER) {
            assertThat(queryStore.getQueryId(metric)).isEqualTo(metric.name.lowercase())
        } else {
            assertThat(queryStore.getQueryId(metric)).isEqualTo("brandPartner")
        }
    }
}
