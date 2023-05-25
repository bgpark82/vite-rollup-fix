package com.musinsa.stat.search.service

import com.musinsa.stat.search.service.SearchQueryGenerator.replaceBrand
import com.musinsa.stat.search.service.SearchQueryGenerator.replacePartner
import com.musinsa.stat.search.service.SearchQueryGenerator.replaceTag
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
        val 결과값 = replaceBrand(브랜드_검색_쿼리, "무신")

        // then
        assertThat(결과값).isEqualTo(
            """
            WHERE brand LIKE '%무신%' or brand_nm LIKE '%무신%'
        """.trimIndent()
        )
    }

    @Test
    fun 업체를_검색_쿼리_예약어에_추가한다() {
        // given
        val 업체_검색_쿼리 = """
            WHERE com_id LIKE '%{{partnerId}}%' or com_nm LIKE '%{{partnerName}}%'
        """.trimIndent()

        // when
        val 결과값 = replacePartner(업체_검색_쿼리, "musinsa")

        // then
        assertThat(결과값).isEqualTo(
            """
            WHERE com_id LIKE '%musinsa%' or com_nm LIKE '%musinsa%'
        """.trimIndent()
        )
    }

    @Test
    fun 태그를_검색_쿼리_예약어에_추가한다() {
        // given
        val 태그_검색_쿼리 = """
            WHERE tag LIKE '%{{tag}}%'
        """.trimIndent()

        // when
        val 결과값 = replaceTag(태그_검색_쿼리, "스트라이프")

        // then
        assertThat(결과값).isEqualTo(
            """
            WHERE tag LIKE '%스트라이프%'
        """.trimIndent()
        )
    }
}