package com.musinsa.stat.sales.service

import com.musinsa.stat.sales.domain.SalesStart
import com.musinsa.stat.sales.fixture.Query.SAMPLE_QUERY
import com.musinsa.stat.sales.fixture.Query.SAMPLE_QUERY_EMPTY_CATEGORY
import com.musinsa.stat.sales.fixture.Query.SAMPLE_QUERY_EMPTY_PARTNER_ID
import com.musinsa.stat.sales.fixture.Query.SAMPLE_QUERY_EMPTY_TAG
import com.musinsa.stat.sales.fixture.Query.SAMPLE_QUERY_SET_CATEGORY
import com.musinsa.stat.sales.fixture.Query.SAMPLE_QUERY_SET_PARTNER_ID
import com.musinsa.stat.sales.fixture.Query.SAMPLE_QUERY_SET_SALES_START
import com.musinsa.stat.sales.fixture.Query.SAMPLE_QUERY_SET_START_END_DATE
import com.musinsa.stat.sales.fixture.Query.SAMPLE_QUERY_SET_TAG
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.NullAndEmptySource
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

internal class QueryGeneratorTest {
    private val queryGenerator = QueryGenerator

    @Test
    fun 배열에서_특정_문자열이_속한_INDEX를_찾는다() {
        // given
        val 임의의_배열 =
            arrayListOf(
                "om.ord_state_date >= '{{startDate}}'",
                "AND om.ord_state_date <= '{{endDate}}'",
                "-- 태그(String List)",
                "AND gt.tag IN ({{tag}})"
            )
        val 찾을_문자 = "{{endDate}}"

        // when, then
        assertThat(queryGenerator.getStringLineNumber(임의의_배열, 찾을_문자)).isEqualTo(
            1
        )
    }

    @Test
    fun 사용하지_않는_조건을_주석처리한다() {
        // given
        val 임의의_배열 =
            arrayListOf(
                "om.ord_state_date >= '{{startDate}}'",
                "AND om.ord_state_date <= '{{endDate}}'",
                "-- 태그(String List)",
                "AND gt.tag IN ({{tag}})"
            )
        val 주석처리할_인덱스 = 1
        val 기댓값_주석처리된_쿼리 = """
              om.ord_state_date >= '{{startDate}}'
              --AND om.ord_state_date <= '{{endDate}}'
              -- 태그(String List)
              AND gt.tag IN ({{tag}})
        """.trimIndent()

        // when, then
        assertThat(
            queryGenerator.annotateUnusedWhereCondition(
                임의의_배열,
                주석처리할_인덱스
            )
        ).isEqualTo(기댓값_주석처리된_쿼리)
    }

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
            anyOrNull(),
            anyOrNull(),
            anyOrNull(),
            anyOrNull(),
            anyOrNull(),
            anyOrNull(),
            anyOrNull()
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
    fun 태그가_존재하지_않으면_쿼리에서_주석처리_된다() {
        assertThat(
            queryGenerator.addTag(
                SAMPLE_QUERY,
                emptyList()
            )
        ).isEqualTo(SAMPLE_QUERY_EMPTY_TAG)
    }

    @Test
    fun 매출시점_추가() {
        assertThat(
            queryGenerator.addSalesStart(
                SAMPLE_QUERY,
                SalesStart.PURCHASE_CONFIRM
            )
        ).isEqualTo(SAMPLE_QUERY_SET_SALES_START)
    }

    @Test
    fun 업체_추가() {
        assertThat(
            queryGenerator.addPartnerId(
                SAMPLE_QUERY,
                "musinsastandard"
            )
        ).isEqualTo(SAMPLE_QUERY_SET_PARTNER_ID)
    }

    @ParameterizedTest
    @NullAndEmptySource
    fun 업체가_존재하지_않으면_쿼리에서_주석처리_된다(partnerId: String?) {
        assertThat(
            queryGenerator.addPartnerId(
                SAMPLE_QUERY,
                partnerId
            )
        ).isEqualTo(SAMPLE_QUERY_EMPTY_PARTNER_ID)
    }

    @Test
    fun 카테고리_추가() {
        assertThat(
            queryGenerator.addCategory(
                SAMPLE_QUERY,
                "청/데님 팬츠"
            )
        ).isEqualTo(SAMPLE_QUERY_SET_CATEGORY)
    }

    @ParameterizedTest
    @NullAndEmptySource
    fun 카테고리가_존재하지_않으면_쿼리에서_주석처리_된다(category: String?) {
        assertThat(
            queryGenerator.addCategory(
                SAMPLE_QUERY,
                category
            )
        ).isEqualTo(SAMPLE_QUERY_EMPTY_CATEGORY)
    }

    @Test
    fun 스타일넘버_추가() {

    }

    @Test
    fun 스타일넘버가_존재하지_않으면_쿼리에서_주석처리_된다() {

    }

    @Test
    fun 상품코드_추가() {

    }

    @Test
    fun 상품코드가_존재하지_않으면_쿼리에서_주석처리_된다() {

    }

    @Test
    fun 브랜드_추가() {

    }

    @Test
    fun 브랜드가_존재하지_않으면_쿼리에서_주석처리_된다() {

    }

    @Test
    fun 쿠폰_추가() {

    }

    @Test
    fun 쿠폰이_존재하지_않으면_쿼리에서_주석처리_된다() {

    }

    @Test
    fun 광고코드_추가() {

    }

    @Test
    fun 광고코드가_존재하지_않으면_쿼리에서_주석처리_된다() {

    }

    @Test
    fun 전문관코드_추가() {

    }

    @Test
    fun 전문관코드가_존재하지_않으면_쿼리에서_주석처리_된다() {

    }

    @Test
    fun 담당MD_추가() {

    }

    @Test
    fun 담당MD가_존재하지_않으면_쿼리에서_주석처리_된다() {

    }

    @Test
    fun 정렬키_추가() {

    }

    @Test
    fun 페이지_원소수_추가() {

    }

    @Test
    fun 페이지_번호_추가() {

    }
}