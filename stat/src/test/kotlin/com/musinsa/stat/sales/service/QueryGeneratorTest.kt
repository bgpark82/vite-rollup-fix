package com.musinsa.stat.sales.service

import com.musinsa.stat.sales.domain.SalesStart
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.NullAndEmptySource

internal class QueryGeneratorTest {
    @Test
    fun 배열에서_특정_문자열이_속한_INDEX_를_찾는다() {
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
        assertThat(QueryGenerator.getStringLineNumber(임의의_배열, 찾을_문자)).isEqualTo(
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
            QueryGenerator.annotateUnusedWhereCondition(
                임의의_배열,
                주석처리할_인덱스
            )
        ).isEqualTo(기댓값_주석처리된_쿼리)
    }

    @Test
    fun 시작날짜와_종료날짜가_설정된다() {
        val 쿼리 = """
          om.ord_state_date >= '{{startDate}}'
          AND om.ord_state_date <= '{{endDate}}'
        """.trimIndent()

        val 변경된_쿼리 = QueryGenerator.applyStarDateAndEndDate(
            쿼리,
            "20230501",
            "20230509"
        )

        assertThat(변경된_쿼리).isEqualTo(
            """
          om.ord_state_date >= '20230501'
          AND om.ord_state_date <= '20230509'
        """.trimIndent()
        )
    }

    @Test
    fun 태그가_설정된다() {
        val 쿼리 = "AND gt.tag IN ({{tag}})"

        val 변경된_쿼리 =
            QueryGenerator.applyTagOrAnnotate(쿼리, arrayListOf("청바지", "반소매티"))

        assertThat(변경된_쿼리).isEqualTo("AND gt.tag IN ('청바지', '반소매티')")
    }

    @Test
    fun 태그가_존재하지_않으면_WHERE_쿼리에서_주석처리_되고_FROM_대상에서도_주석처리_된다() {
        val 쿼리 = """
        FROM datamart.datamart.orders_merged om
          JOIN datamart.datamart.goods_tags as gt
            ON om.goods_no = gt.goods_no
          JOIN datamart.datamart.coupon as c
            ON om.coupon_no = c.coupon_no
          JOIN datamart.datamart.specialty_goods as sg
            ON om.goods_no = sg.goods_no

        WHERE 
          -- 일자
          om.ord_state_date >= '{{startDate}}'
          AND om.ord_state_date <= '{{endDate}}'

          -- 태그(String List)
          AND gt.tag IN ({{tag}})
        """.trimIndent()

        val 변경된_쿼리 = QueryGenerator.applyTagOrAnnotate(
            쿼리,
            emptyList()
        )

        assertThat(변경된_쿼리).isEqualTo(
            """
            FROM datamart.datamart.orders_merged om
            --  JOIN datamart.datamart.goods_tags as gt
            --    ON om.goods_no = gt.goods_no
              JOIN datamart.datamart.coupon as c
                ON om.coupon_no = c.coupon_no
              JOIN datamart.datamart.specialty_goods as sg
                ON om.goods_no = sg.goods_no

            WHERE 
              -- 일자
              om.ord_state_date >= '{{startDate}}'
              AND om.ord_state_date <= '{{endDate}}'

              -- 태그(String List)
            --  AND gt.tag IN ({{tag}})
        """.trimIndent()
        )
    }

    @Test
    fun 매출시점_추가() {
        val 쿼리 =
            "AND if('{{salesStart}}'='SHIPPING_REQUEST', om.state_order, om.state_order_done) = True"

        val 변경된_쿼리 =
            QueryGenerator.applySalesStart(쿼리, SalesStart.PURCHASE_CONFIRM)

        assertThat(변경된_쿼리).isEqualTo("AND if('PURCHASE_CONFIRM'='SHIPPING_REQUEST', om.state_order, om.state_order_done) = True")
    }

    @Test
    fun 업체_추가() {
        val 쿼리 = "AND om.com_id = '{{partnerId}}'"

        val 변경된_쿼리 =
            QueryGenerator.applyPartnerIdOrAnnotate(쿼리, "musinsastandard")

        assertThat(변경된_쿼리).isEqualTo("AND om.com_id = 'musinsastandard'")
    }

    @ParameterizedTest
    @NullAndEmptySource
    fun 업체가_존재하지_않으면_쿼리에서_주석처리_된다(partnerId: String?) {
        val 쿼리 = """
          -- 업체
          AND om.com_id = '{{partnerId}}'
        """.trimIndent()

        val 변경된_쿼리 = QueryGenerator.applyPartnerIdOrAnnotate(쿼리, partnerId)

        assertThat(변경된_쿼리).isEqualTo(
            """
          -- 업체
          --AND om.com_id = '{{partnerId}}'
        """.trimIndent()
        )
    }

    @Test
    fun 카테고리_추가() {
        val 쿼리 = "AND om.small_nm = '{{category}}'"

        val 변경된_쿼리 = QueryGenerator.applyCategoryOrAnnotate(쿼리, "청/데님 팬츠")

        assertThat(변경된_쿼리).isEqualTo("AND om.small_nm = '청/데님 팬츠'")
    }

    @ParameterizedTest
    @NullAndEmptySource
    fun 카테고리가_존재하지_않으면_쿼리에서_주석처리_된다(category: String?) {
        val 쿼리 = """
          -- 카테고리
          AND om.small_nm = '{{category}}'
        """.trimIndent()

        val 변경된_쿼리 = QueryGenerator.applyCategoryOrAnnotate(쿼리, category)

        assertThat(변경된_쿼리).isEqualTo(
            """
            -- 카테고리
            --AND om.small_nm = '{{category}}'
        """.trimIndent()
        )
    }

    @Test
    fun 스타일넘버_추가() {
        val 쿼리 = "AND om.style_no = '{{styleNumber}}'"

        val 변경된_쿼리 = QueryGenerator.applyStyleNumberOrAnnotate(
            쿼리, "DF22SS7022"
        )

        assertThat(변경된_쿼리).isEqualTo("AND om.style_no = 'DF22SS7022'")
    }

    @ParameterizedTest
    @NullAndEmptySource
    fun 스타일넘버가_존재하지_않으면_쿼리에서_주석처리_된다(styleNumber: String?) {
        val 쿼리 = """
          -- 스타일넘버
          AND om.style_no = '{{styleNumber}}'
        """.trimIndent()

        val 변경된_쿼리 = QueryGenerator.applyStyleNumberOrAnnotate(쿼리, styleNumber)

        assertThat(변경된_쿼리).isEqualTo(
            """
            -- 스타일넘버
            --AND om.style_no = '{{styleNumber}}'
        """.trimIndent()
        )
    }

    @Test
    fun 상품코드_추가() {
        val 쿼리 = "AND om.goods_no = '{{goodsNumber}}'"

        val 변경된_쿼리 = QueryGenerator.applyGoodsNumberOrAnnotate(쿼리, "1387960")

        assertThat(변경된_쿼리).isEqualTo("AND om.goods_no = '1387960'")
    }

    @ParameterizedTest
    @NullAndEmptySource
    fun 상품코드가_존재하지_않으면_쿼리에서_주석처리_된다(goodsNumber: String?) {
        val 쿼리 = """
          -- 상품코드
          AND om.goods_no = '{{goodsNumber}}'
        """.trimIndent()

        val 변경된_쿼리 = QueryGenerator.applyGoodsNumberOrAnnotate(쿼리, goodsNumber)

        assertThat(변경된_쿼리).isEqualTo(
            """
            -- 상품코드
            --AND om.goods_no = '{{goodsNumber}}'
        """.trimIndent()
        )
    }

    @Test
    fun 브랜드_추가() {
        val 쿼리 = "AND om.brand = '{{brandId}}'"

        val 변경된_쿼리 =
            QueryGenerator.applyBrandIdOrAnnotate(쿼리, "musinsastandard")

        assertThat(변경된_쿼리).isEqualTo("AND om.brand = 'musinsastandard'")
    }

    @ParameterizedTest
    @NullAndEmptySource
    fun 브랜드가_존재하지_않으면_쿼리에서_주석처리_된다(brandId: String?) {
        val 쿼리 = """
            -- 브랜드
            AND om.brand = '{{brandId}}'
        """.trimIndent()

        val 변경된_쿼리 = QueryGenerator.applyBrandIdOrAnnotate(쿼리, brandId)

        assertThat(변경된_쿼리).isEqualTo(
            """
            -- 브랜드
            --AND om.brand = '{{brandId}}'
        """.trimIndent()
        )
    }

    @Test
    fun 쿠폰_추가() {
        val 쿼리 = "AND c.coupon_no = '{{couponNumber}}'"

        val 변경된_쿼리 = QueryGenerator.applyCouponNumberOrAnnotate(쿼리, "72852")

        assertThat(변경된_쿼리).isEqualTo("AND c.coupon_no = '72852'")
    }

    @ParameterizedTest
    @NullAndEmptySource
    fun 쿠폰이_존재하지_않으면_쿼리에서_주석처리_된다(couponNumber: String?) {
        val 쿼리 = """
          -- 쿠폰
          AND c.coupon_no = '{{couponNumber}}'
        """.trimIndent()

        val 변경된_쿼리 =
            QueryGenerator.applyCouponNumberOrAnnotate(쿼리, couponNumber)

        assertThat(변경된_쿼리).isEqualTo(
            """
            -- 쿠폰
            --AND c.coupon_no = '{{couponNumber}}'
        """.trimIndent()
        )
    }

    @Test
    fun 광고코드_추가() {
        val 쿼리 = "AND om.ad_cd = '{{adCode}}'"

        val 변경된_쿼리 = QueryGenerator.applyAdCodeOrAnnotate(쿼리, "REFCRLC003")

        assertThat(변경된_쿼리).isEqualTo("AND om.ad_cd = 'REFCRLC003'")
    }

    @ParameterizedTest
    @NullAndEmptySource
    fun 광고코드가_존재하지_않으면_쿼리에서_주석처리_된다(adCode: String?) {
        val 쿼리 = """
            -- 광고코드
            AND om.ad_cd = '{{adCode}}'
        """.trimIndent()

        val 변경된_쿼리 = QueryGenerator.applyAdCodeOrAnnotate(쿼리, adCode)

        assertThat(변경된_쿼리).isEqualTo(
            """
            -- 광고코드
            --AND om.ad_cd = '{{adCode}}'
        """.trimIndent()
        )
    }

    @Test
    fun 전문관코드_추가() {
        val 쿼리 = "AND sg.specialty_cd = '{{specialtyCode}}'"

        val 변경된_쿼리 = QueryGenerator.applySpecialtyCodeOrAnnotate(쿼리, "golf")

        assertThat(변경된_쿼리).isEqualTo("AND sg.specialty_cd = 'golf'")
    }

    @ParameterizedTest
    @NullAndEmptySource
    fun 전문관코드가_존재하지_않으면_쿼리에서_주석처리_된다(specialtyCode: String?) {
        val 쿼리 = """
            -- 전문관코드
            AND sg.specialty_cd = '{{specialtyCode}}'
        """.trimIndent()

        val 변경된_쿼리 =
            QueryGenerator.applySpecialtyCodeOrAnnotate(쿼리, specialtyCode)

        assertThat(변경된_쿼리).isEqualTo(
            """
            -- 전문관코드
            --AND sg.specialty_cd = '{{specialtyCode}}'
        """.trimIndent()
        )
    }

    @Test
    fun 담당MD_추가() {
        val 쿼리 = "AND om.md_id = '{{mdId}}'"

        val 변경된_쿼리 = QueryGenerator.applyMdIdOrAnnotate(쿼리, "woo.choi")

        assertThat(변경된_쿼리).isEqualTo("AND om.md_id = 'woo.choi'")
    }

    @ParameterizedTest
    @NullAndEmptySource
    fun 담당MD_가_존재하지_않으면_쿼리에서_주석처리_된다(mdId: String?) {
        val 쿼리 = """
            -- 담당MD
            AND om.md_id = '{{mdId}}'
        """.trimIndent()

        val 변경된_쿼리 = QueryGenerator.applyMdIdOrAnnotate(쿼리, mdId)

        assertThat(변경된_쿼리).isEqualTo(
            """
            -- 담당MD
            --AND om.md_id = '{{mdId}}'
        """.trimIndent()
        )
    }

    @Test
    fun 정렬키_추가() {
        val 쿼리 = "ORDER BY `{{orderBy}}` ASC"

        val 변경된_쿼리 = QueryGenerator.applyOrderKey(쿼리, "date")

        assertThat(변경된_쿼리).isEqualTo("ORDER BY `date` ASC")
    }
}