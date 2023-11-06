package com.musinsa.stat.sales.dto

import com.musinsa.stat.sales.controller.PAGE_DEFAULT_VALUE
import com.musinsa.stat.sales.controller.PAGE_SIZE_DEFAULT_VALUE
import com.musinsa.stat.sales.fixture.CategoryFixture.쿼리_결과_카테고리_리스트
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class CategorySalesStatisticsResponseTest {

    @Test
    fun `대분류 값이 없는 경우 결과값 Row 에서 삭제한다`() {
        val 원본_카테고리_리스트 = 쿼리_결과_카테고리_리스트()

        val 정제된_응답값 = CategorySalesStatisticsResponse(
            원본_카테고리_리스트,
            PAGE_SIZE_DEFAULT_VALUE.toLong(),
            PAGE_DEFAULT_VALUE.toLong(),
            "originSql"
        )

        assertThat(정제된_응답값.content.size).isEqualTo(14)
    }
}
