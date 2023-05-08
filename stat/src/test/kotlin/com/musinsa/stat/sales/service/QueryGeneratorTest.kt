package com.musinsa.stat.sales.service

import com.musinsa.stat.sales.fixture.Query.SAMPLE_QUERY
import com.musinsa.stat.sales.fixture.QueryReplaced.SAMPLE_QUERY_SET_START_END_DATE
import com.musinsa.stat.sales.fixture.QueryReplaced.SAMPLE_QUERY_SET_TAG
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

internal class QueryGeneratorTest {
    private val queryGenerator = QueryGenerator

    @Test
    fun 쿼리_치환_함수를_모두_호출한다() {
        queryGenerator.generate(
            SAMPLE_QUERY,
            "20230501",
            "20230509",
            arrayListOf<String>("청바지", "반소매티"),
            anyOrNull(),
            anyOrNull(),
            anyOrNull(),
            anyOrNull(),
            anyOrNull(),
            anyOrNull(),
            anyOrNull(), anyOrNull(), anyOrNull(), anyOrNull()
        )
        verify(queryGenerator, times(1)).addStarDateAndEndDate(
            anyOrNull(),
            anyOrNull(),
            anyOrNull()
        )
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

    @Test
    fun 태그가_설정된다() {
        assertThat(
            queryGenerator.addTag(
                SAMPLE_QUERY,
                arrayListOf<String>("청바지", "반소매티")
            )
        ).isEqualTo(SAMPLE_QUERY_SET_TAG)
    }

    @Test
    fun 태그가_존재하지_않으면_쿼리에서_삭제된다() {

    }
}