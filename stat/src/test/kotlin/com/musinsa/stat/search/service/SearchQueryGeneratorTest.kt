package com.musinsa.stat.search.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

private class SearchQueryGeneratorTest {

    @Test
    fun 브랜드를_검색_쿼리_예약어에_추가한다() {
        // given
        val 브랜드_검색_쿼리 = """
            WHERE brand LIKE '%{{brandId}}%' or brand_nm LIKE '%{{brandName}}%'
        """.trimIndent()

        // when
        val 결과값 = SearchQueryGenerator.replaceBrand(브랜드_검색_쿼리, "무신")

        // then
        assertThat(결과값).isEqualTo(
            """
            WHERE brand LIKE '%무신%' or brand_nm LIKE '%무신%'
        """.trimIndent()
        )

    }
}