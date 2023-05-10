package com.musinsa.stat.sales.service

import com.musinsa.stat.sales.domain.SalesStart
import com.musinsa.stat.sales.fixture.Query.SAMPLE_QUERY
import com.musinsa.stat.sales.fixture.Query.SAMPLE_QUERY_EMPTY_AD_CODE
import com.musinsa.stat.sales.fixture.Query.SAMPLE_QUERY_EMPTY_BRAND_ID
import com.musinsa.stat.sales.fixture.Query.SAMPLE_QUERY_EMPTY_CATEGORY
import com.musinsa.stat.sales.fixture.Query.SAMPLE_QUERY_EMPTY_COUPON_NUMBER
import com.musinsa.stat.sales.fixture.Query.SAMPLE_QUERY_EMPTY_GOODS_NUMBER
import com.musinsa.stat.sales.fixture.Query.SAMPLE_QUERY_EMPTY_MD_ID
import com.musinsa.stat.sales.fixture.Query.SAMPLE_QUERY_EMPTY_PARTNER_ID
import com.musinsa.stat.sales.fixture.Query.SAMPLE_QUERY_EMPTY_SPECIALTY_CODE
import com.musinsa.stat.sales.fixture.Query.SAMPLE_QUERY_EMPTY_STYLE_NUMBER
import com.musinsa.stat.sales.fixture.Query.SAMPLE_QUERY_EMPTY_TAG
import com.musinsa.stat.sales.fixture.Query.SAMPLE_QUERY_SET_AD_CODE
import com.musinsa.stat.sales.fixture.Query.SAMPLE_QUERY_SET_BRAND_ID
import com.musinsa.stat.sales.fixture.Query.SAMPLE_QUERY_SET_CATEGORY
import com.musinsa.stat.sales.fixture.Query.SAMPLE_QUERY_SET_COUPON_NUMBER
import com.musinsa.stat.sales.fixture.Query.SAMPLE_QUERY_SET_GOODS_NUMBER
import com.musinsa.stat.sales.fixture.Query.SAMPLE_QUERY_SET_MD_ID
import com.musinsa.stat.sales.fixture.Query.SAMPLE_QUERY_SET_PAGING_PARAMS
import com.musinsa.stat.sales.fixture.Query.SAMPLE_QUERY_SET_PARTNER_ID
import com.musinsa.stat.sales.fixture.Query.SAMPLE_QUERY_SET_SALES_START
import com.musinsa.stat.sales.fixture.Query.SAMPLE_QUERY_SET_SPECIALTY_CODE
import com.musinsa.stat.sales.fixture.Query.SAMPLE_QUERY_SET_START_END_DATE
import com.musinsa.stat.sales.fixture.Query.SAMPLE_QUERY_SET_STYLE_NUMBER
import com.musinsa.stat.sales.fixture.Query.SAMPLE_QUERY_SET_TAG
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.NullAndEmptySource

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
        assertThat(
            queryGenerator.addStyleNumber(
                SAMPLE_QUERY,
                "DF22SS7022"
            )
        ).isEqualTo(SAMPLE_QUERY_SET_STYLE_NUMBER)
    }

    @ParameterizedTest
    @NullAndEmptySource
    fun 스타일넘버가_존재하지_않으면_쿼리에서_주석처리_된다(styleNumber: String?) {
        assertThat(
            queryGenerator.addStyleNumber(
                SAMPLE_QUERY,
                styleNumber
            )
        ).isEqualTo(SAMPLE_QUERY_EMPTY_STYLE_NUMBER)
    }

    @Test
    fun 상품코드_추가() {
        assertThat(
            queryGenerator.addGoodsNumber(
                SAMPLE_QUERY,
                "1387960"
            )
        ).isEqualTo(SAMPLE_QUERY_SET_GOODS_NUMBER)
    }

    @ParameterizedTest
    @NullAndEmptySource
    fun 상품코드가_존재하지_않으면_쿼리에서_주석처리_된다(goodsNumber: String?) {
        assertThat(
            queryGenerator.addGoodsNumber(
                SAMPLE_QUERY,
                goodsNumber
            )
        ).isEqualTo(SAMPLE_QUERY_EMPTY_GOODS_NUMBER)
    }

    @Test
    fun 브랜드_추가() {
        assertThat(
            queryGenerator.addBrandId(
                SAMPLE_QUERY,
                "musinsastandard"
            )
        ).isEqualTo(SAMPLE_QUERY_SET_BRAND_ID)
    }

    @ParameterizedTest
    @NullAndEmptySource
    fun 브랜드가_존재하지_않으면_쿼리에서_주석처리_된다(brandId: String?) {
        assertThat(
            queryGenerator.addBrandId(
                SAMPLE_QUERY,
                brandId
            )
        ).isEqualTo(SAMPLE_QUERY_EMPTY_BRAND_ID)
    }

    @Test
    fun 쿠폰_추가() {
        assertThat(
            queryGenerator.addCouponNumber(
                SAMPLE_QUERY,
                "72852"
            )
        ).isEqualTo(SAMPLE_QUERY_SET_COUPON_NUMBER)
    }

    @ParameterizedTest
    @NullAndEmptySource
    fun 쿠폰이_존재하지_않으면_쿼리에서_주석처리_된다(couponNumber: String?) {
        assertThat(
            queryGenerator.addCouponNumber(
                SAMPLE_QUERY,
                couponNumber
            )
        ).isEqualTo(SAMPLE_QUERY_EMPTY_COUPON_NUMBER)
    }

    @Test
    fun 광고코드_추가() {
        assertThat(
            queryGenerator.addAdCode(
                SAMPLE_QUERY,
                "REFCRLC003"
            )
        ).isEqualTo(SAMPLE_QUERY_SET_AD_CODE)
    }

    @ParameterizedTest
    @NullAndEmptySource
    fun 광고코드가_존재하지_않으면_쿼리에서_주석처리_된다(adCode: String?) {
        assertThat(
            queryGenerator.addAdCode(
                SAMPLE_QUERY,
                adCode
            )
        ).isEqualTo(SAMPLE_QUERY_EMPTY_AD_CODE)
    }

    @Test
    fun 전문관코드_추가() {
        assertThat(
            queryGenerator.addSpecialtyCode(
                SAMPLE_QUERY,
                "golf"
            )
        ).isEqualTo(SAMPLE_QUERY_SET_SPECIALTY_CODE)
    }

    @ParameterizedTest
    @NullAndEmptySource
    fun 전문관코드가_존재하지_않으면_쿼리에서_주석처리_된다(specialtyCode: String?) {
        assertThat(
            queryGenerator.addSpecialtyCode(
                SAMPLE_QUERY,
                specialtyCode
            )
        ).isEqualTo(SAMPLE_QUERY_EMPTY_SPECIALTY_CODE)
    }

    @Test
    fun 담당MD_추가() {
        assertThat(
            queryGenerator.addMdId(
                SAMPLE_QUERY,
                "woo.choi"
            )
        ).isEqualTo(SAMPLE_QUERY_SET_MD_ID)
    }

    @ParameterizedTest
    @NullAndEmptySource
    fun 담당MD가_존재하지_않으면_쿼리에서_주석처리_된다(mdId: String?) {
        assertThat(
            queryGenerator.addMdId(
                SAMPLE_QUERY,
                mdId
            )
        ).isEqualTo(SAMPLE_QUERY_EMPTY_MD_ID)
    }

    @Test
    fun 페이징_파라미터_추가() {
        assertThat(
            queryGenerator.addPagingParams(SAMPLE_QUERY, "date")
        ).isEqualTo(SAMPLE_QUERY_SET_PAGING_PARAMS)
    }
}