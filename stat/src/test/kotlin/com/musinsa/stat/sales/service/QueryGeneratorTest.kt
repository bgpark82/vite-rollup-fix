package com.musinsa.stat.sales.service

import com.musinsa.stat.sales.fixture.Query.SAMPLE_QUERY
import com.musinsa.stat.sales.fixture.QueryReplaced.SAMPLE_QUERY_SET_START_END_DATE
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class QueryGeneratorTest {
    private val queryGenerator = QueryGenerator

    @Test
    fun 쿼리_치환_함수를_모두_호출한다() {

    }

    @Test
    fun 시작날짜와_종료날짜가_설정된다() {
        assertThat(
            queryGenerator.addStarDateAndEndDate(
                SAMPLE_QUERY,
                "20230501",
                "20230509"
            )
        ).isEqualTo(SAMPLE_QUERY_SET_START_END_DATE)
    }
}