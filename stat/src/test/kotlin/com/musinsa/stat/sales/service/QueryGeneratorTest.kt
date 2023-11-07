package com.musinsa.stat.sales.service

import com.musinsa.stat.sales.domain.GoodsKind
import com.musinsa.stat.sales.domain.Metric
import com.musinsa.stat.sales.domain.OrderBy
import com.musinsa.stat.sales.domain.OrderDirection
import com.musinsa.stat.sales.domain.PartnerType
import com.musinsa.stat.sales.domain.SalesFunnel
import com.musinsa.stat.sales.domain.SalesStart
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import org.junit.jupiter.params.provider.ValueSource
import java.time.LocalDate

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
    fun 태그가_설정된다_그리고_JOIN_주석_제거() {
        val 쿼리 = """
            --{{joinGoodsTags}}JOIN datamart.datamart.goods_tags as gt ON om.goods_no = gt.goods_no
            AND gt.tag IN ({{tag}})
        """.trimIndent()

        val 변경된_쿼리 =
            QueryGenerator.applyTagOrAnnotate(쿼리, arrayListOf("청바지", "반소매티"))

        assertThat(변경된_쿼리).isEqualTo(
            """
            JOIN datamart.datamart.goods_tags as gt ON om.goods_no = gt.goods_no
            AND gt.tag IN ('청바지', '반소매티')
            """.trimIndent()
        )
    }

    @Test
    fun 태그가_존재하지_않으면_쿼리에서_주석처리_된다() {
        val 쿼리 = """
          -- 태그(String List)
          AND gt.tag IN ({{tag}})
        """.trimIndent()

        val 변경된_쿼리 = QueryGenerator.applyTagOrAnnotate(쿼리, emptyList())

        assertThat(변경된_쿼리).isEqualTo(
            """
            -- 태그(String List)
            --AND gt.tag IN ({{tag}})
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
        val 쿼리 = "AND om.com_id IN ({{partnerId}})"

        val 변경된_쿼리 =
            QueryGenerator.applyPartnerIdOrAnnotate(
                쿼리,
                listOf("musinsastandard", "adidas")
            )

        assertThat(변경된_쿼리).isEqualTo("AND om.com_id IN ('musinsastandard', 'adidas')")
    }

    @Test
    fun 업체가_존재하지_않으면_쿼리에서_주석처리_된다() {
        val 쿼리 = """
          -- 업체
          AND om.com_id IN ({{partnerId}})'
        """.trimIndent()

        val 변경된_쿼리 = QueryGenerator.applyPartnerIdOrAnnotate(쿼리, emptyList())

        assertThat(변경된_쿼리).isEqualTo(
            """
          -- 업체
          --AND om.com_id IN ({{partnerId}})'
            """.trimIndent()
        )
    }

    @Test
    fun 카테고리_추가() {
        val 쿼리 = "AND om.small_nm IN ({{category}})"

        val 변경된_쿼리 =
            QueryGenerator.applyCategoryOrAnnotate(쿼리, listOf("청/데님 팬츠", "핸드백"))

        assertThat(변경된_쿼리).isEqualTo("AND om.small_nm IN ('청/데님 팬츠', '핸드백')")
    }

    @Test
    fun 카테고리가_존재하지_않으면_쿼리에서_주석처리_된다() {
        val 쿼리 = """
          -- 카테고리
          AND om.small_nm IN ({{category}})
        """.trimIndent()

        val 변경된_쿼리 = QueryGenerator.applyCategoryOrAnnotate(쿼리, emptyList())

        assertThat(변경된_쿼리).isEqualTo(
            """
            -- 카테고리
            --AND om.small_nm IN ({{category}})
            """.trimIndent()
        )
    }

    @Test
    fun 스타일넘버_추가() {
        val 쿼리 = "AND om.style_no IN ({{styleNumber}})"

        val 변경된_쿼리 = QueryGenerator.applyStyleNumberOrAnnotate(
            쿼리,
            listOf("DF22SS7022", "ABCDE")
        )

        assertThat(변경된_쿼리).isEqualTo("AND om.style_no IN ('DF22SS7022', 'ABCDE')")
    }

    @Test
    fun 스타일넘버가_존재하지_않으면_쿼리에서_주석처리_된다() {
        val 쿼리 = """
          -- 스타일넘버
          AND om.style_no IN ({{styleNumber}})'
        """.trimIndent()

        val 변경된_쿼리 = QueryGenerator.applyStyleNumberOrAnnotate(쿼리, emptyList())

        assertThat(변경된_쿼리).isEqualTo(
            """
            -- 스타일넘버
            --AND om.style_no IN ({{styleNumber}})'
            """.trimIndent()
        )
    }

    @Test
    fun 상품코드_추가() {
        val 쿼리 = "AND om.goods_no IN ({{goodsNumber}})"

        val 변경된_쿼리 = QueryGenerator.applyGoodsNumberOrAnnotate(
            쿼리,
            listOf("1387960", "224135")
        )

        assertThat(변경된_쿼리).isEqualTo("AND om.goods_no IN ('1387960', '224135')")
    }

    @Test
    fun 상품코드가_존재하지_않으면_쿼리에서_주석처리_된다() {
        val 쿼리 = """
          -- 상품코드
          AND om.goods_no IN ({{goodsNumber}})
        """.trimIndent()

        val 변경된_쿼리 = QueryGenerator.applyGoodsNumberOrAnnotate(쿼리, emptyList())

        assertThat(변경된_쿼리).isEqualTo(
            """
            -- 상품코드
            --AND om.goods_no IN ({{goodsNumber}})
            """.trimIndent()
        )
    }

    @Test
    fun 브랜드_추가() {
        val 쿼리 = "AND om.brand IN ({{brandId}})"

        val 변경된_쿼리 =
            QueryGenerator.applyBrandIdOrAnnotate(
                쿼리,
                listOf("musinsastandard", "adidas")
            )

        assertThat(변경된_쿼리).isEqualTo("AND om.brand IN ('musinsastandard', 'adidas')")
    }

    @Test
    fun 브랜드가_존재하지_않으면_쿼리에서_주석처리_된다() {
        val 쿼리 = """
            -- 브랜드
            AND om.brand IN ({{brandId}})
        """.trimIndent()

        val 변경된_쿼리 = QueryGenerator.applyBrandIdOrAnnotate(쿼리, emptyList())

        assertThat(변경된_쿼리).isEqualTo(
            """
            -- 브랜드
            --AND om.brand IN ({{brandId}})
            """.trimIndent()
        )
    }

    @ParameterizedTest
    @EnumSource(
        value = Metric::class,
        mode = EnumSource.Mode.EXCLUDE,
        names = ["COUPON"]
    )
    fun 쿠폰별_매출통계가_아닌경우_쿠폰_추가_그리고_JOIN_주석_제거(metric: Metric) {
        val 쿼리 = """
            --{{joinCoupon}}JOIN datamart.datamart.coupon as c ON om.coupon_no = c.coupon_no
            AND c.coupon_no IN ({{couponNumber}})
        """.trimIndent()

        val 변경된_쿼리 =
            QueryGenerator.applyCouponNumberOrAnnotate(
                쿼리,
                arrayListOf("72852", "12345"),
                metric.name
            )

        assertThat(변경된_쿼리).isEqualTo(
            """
            JOIN datamart.datamart.coupon as c ON om.coupon_no = c.coupon_no
            AND c.coupon_no IN ('72852', '12345')
            """.trimIndent()
        )
    }

    @Test
    fun 쿠폰별_매출통계는_JOIN_주석이_해제되어_있다() {
        val 쿼리 = """
            JOIN datamart.datamart.coupon as c ON om.coupon_no = c.coupon_no
            AND c.coupon_no IN ({{couponNumber}})
        """.trimIndent()

        val 변경된_쿼리 =
            QueryGenerator.applyCouponNumberOrAnnotate(
                쿼리,
                arrayListOf("72852", "12345"),
                Metric.COUPON.name
            )

        assertThat(변경된_쿼리).isEqualTo(
            """
            JOIN datamart.datamart.coupon as c ON om.coupon_no = c.coupon_no
            AND c.coupon_no IN ('72852', '12345')
            """.trimIndent()
        )
    }

    @Test
    fun 쿠폰이_존재하지_않으면_쿼리에서_주석처리_된다() {
        val 쿼리 = """
          --{{joinCoupon}}JOIN datamart.datamart.coupon as c ON om.coupon_no = c.coupon_no
          -- 쿠폰
          AND c.coupon_no IN ({{couponNumber}})
        """.trimIndent()

        val 변경된_쿼리 =
            QueryGenerator.applyCouponNumberOrAnnotate(
                쿼리,
                emptyList(),
                Metric.DAILY.name
            )

        assertThat(변경된_쿼리).isEqualTo(
            """
            --{{joinCoupon}}JOIN datamart.datamart.coupon as c ON om.coupon_no = c.coupon_no
            -- 쿠폰
            --AND c.coupon_no IN ({{couponNumber}})
            """.trimIndent()
        )
    }

    @Test
    fun 광고코드_추가() {
        val 쿼리 = "AND om.ad_cd IN ({{adCode}})"

        val 변경된_쿼리 = QueryGenerator.applyAdCodeOrAnnotate(
            쿼리,
            listOf("REFCRLC003", "GOLF0123")
        )

        assertThat(변경된_쿼리).isEqualTo("AND om.ad_cd IN ('REFCRLC003', 'GOLF0123')")
    }

    @Test
    fun 광고코드가_존재하지_않으면_쿼리에서_주석처리_된다() {
        val 쿼리 = """
            -- 광고코드
            AND om.ad_cd IN ({{adCode}})
        """.trimIndent()

        val 변경된_쿼리 = QueryGenerator.applyAdCodeOrAnnotate(쿼리, emptyList())

        assertThat(변경된_쿼리).isEqualTo(
            """
            -- 광고코드
            --AND om.ad_cd IN ({{adCode}})
            """.trimIndent()
        )
    }

    @Test
    fun 전문관코드_추가_그리고_JOIN_주석_제거() {
        val 쿼리 = """
                --{{joinSpecialtyGoods}}JOIN datamart.datamart.specialty_goods as sg ON om.goods_no = sg.goods_no
                AND sg.specialty_cd IN ({{specialtyCode}})
        """.trimIndent()

        val 변경된_쿼리 =
            QueryGenerator.applySpecialtyCodeOrAnnotate(쿼리, arrayListOf("golf"))

        assertThat(변경된_쿼리).isEqualTo(
            """
            JOIN datamart.datamart.specialty_goods as sg ON om.goods_no = sg.goods_no
            AND sg.specialty_cd IN ('golf')
            """.trimIndent()
        )
    }

    @Test
    fun 전문관코드가_존재하지_않으면_쿼리에서_주석처리_된다() {
        val 쿼리 = """
            -- 전문관코드
            AND sg.specialty_cd IN ({{specialtyCode}})
        """.trimIndent()

        val 변경된_쿼리 =
            QueryGenerator.applySpecialtyCodeOrAnnotate(쿼리, emptyList())

        assertThat(변경된_쿼리).isEqualTo(
            """
            -- 전문관코드
            --AND sg.specialty_cd IN ({{specialtyCode}})
            """.trimIndent()
        )
    }

    @Test
    fun 담당MD_추가() {
        val 쿼리 = "AND om.md_id IN ({{mdId}})"

        val 변경된_쿼리 = QueryGenerator.applyMdIdOrAnnotate(
            쿼리,
            listOf("woo.choi", "woo1.choi")
        )

        assertThat(변경된_쿼리).isEqualTo("AND om.md_id IN ('woo.choi', 'woo1.choi')")
    }

    @Test
    fun 담당MD_가_존재하지_않으면_쿼리에서_주석처리_된다() {
        val 쿼리 = """
            -- 담당MD
            AND om.md_id IN ({{mdId}})
        """.trimIndent()

        val 변경된_쿼리 = QueryGenerator.applyMdIdOrAnnotate(쿼리, emptyList())

        assertThat(변경된_쿼리).isEqualTo(
            """
            -- 담당MD
            --AND om.md_id IN ({{mdId}})
            """.trimIndent()
        )
    }

    @Test
    fun 페이징_파라미터_추가() {
        val 쿼리 = """
            ORDER BY `{{orderBy}}` {{orderDirection}}

            LIMIT {{pageSize}}

            OFFSET {{page}}
        """.trimIndent()

        val 변경된_쿼리 = QueryGenerator.applyPagingParams(
            쿼리,
            "date",
            OrderDirection.DESC.name,
            100,
            1
        )

        assertThat(변경된_쿼리).isEqualTo(
            """
            ORDER BY `date` DESC

            LIMIT 100

            OFFSET 1
            """.trimIndent()
        )
    }

    @ParameterizedTest
    @EnumSource(value = PartnerType::class)
    fun `업체 구분 파라미터 추가`(업체구분: PartnerType) {
        val 쿼리 = "  AND om.com_type_cd = {{partnerType}}"

        val 변경된_쿼리 = QueryGenerator.applyPartnerType(쿼리, 업체구분.code.toString())

        assertThat(변경된_쿼리).isEqualTo("  AND om.com_type_cd = ${업체구분.code}")
    }

    @Test
    fun `업체 구분 파라미터가 존재하지 않으면 쿼리에서 주석처리 된다`() {
        val 쿼리 = """
                -- 업체구분
                AND om.com_type_cd = {{partnerType}}
        """.trimIndent()

        val 변경된_쿼리 = QueryGenerator.applyPartnerType(쿼리, null)

        assertThat(변경된_쿼리).isEqualTo(
            """
                -- 업체구분
                --AND om.com_type_cd = {{partnerType}}
            """.trimIndent()
        )
    }

    @ParameterizedTest
    @EnumSource(value = GoodsKind::class)
    fun `품목 파라미터 추가`(품목: GoodsKind) {
        val 쿼리 = "  AND om.opt_kind_cd = '{{goodsKind}}'"

        val 변경된_쿼리 = QueryGenerator.applyGoodsKind(쿼리, 품목.description)

        assertThat(변경된_쿼리).isEqualTo("  AND om.opt_kind_cd = '${품목.description}'")
    }

    @Test
    fun `품목 파라미터가 존재하지 않으면 쿼리에서 주석처리 된다`() {
        val 쿼리 = """
            -- 품목(상품 옵션)
            AND om.opt_kind_cd = '{{goodsKind}}'
        """.trimIndent()

        val 변경된_쿼리 = QueryGenerator.applyGoodsKind(쿼리, null)

        assertThat(변경된_쿼리).isEqualTo(
            """
            -- 품목(상품 옵션)
            --AND om.opt_kind_cd = '{{goodsKind}}'
            """.trimIndent()
        )
    }

    @ParameterizedTest
    @ValueSource(longs = [60 * 60 * 3, 60 * 60 * 24, 60 * 60 * 24 * 7])
    fun `광고집계시간이 존재하면 광고집계시간 파라미터 추가 및 JOIN 주석 해제한다`(광고집계시간: Long) {
        val 쿼리 = """
            --{{joinAdHours}}LEFT JOIN datamart.datamart.order_track ot on om.ord_no = ot.ord_no
            -- 광고집계시간
            AND ot.advt <= {{adHours}}
        """.trimIndent()

        val 변경된_쿼리 = QueryGenerator.applyAdHours(쿼리, 광고집계시간)

        assertThat(변경된_쿼리).isEqualTo(
            """
            LEFT JOIN datamart.datamart.order_track ot on om.ord_no = ot.ord_no
            -- 광고집계시간
            AND ot.advt <= $광고집계시간
            """.trimIndent()
        )
    }

    @Test
    fun `광고집계시간이 없으면 주석처리한다`() {
        val 쿼리 = """
            --{{joinAdHours}}LEFT JOIN datamart.datamart.order_track ot on om.ord_no = ot.ord_no
            -- 광고집계시간
            AND ot.advt <= {{adHours}}
        """.trimIndent()

        val 변경된_쿼리 = QueryGenerator.applyAdHours(쿼리, null)

        assertThat(변경된_쿼리).isEqualTo(
            """
            --{{joinAdHours}}LEFT JOIN datamart.datamart.order_track ot on om.ord_no = ot.ord_no
            -- 광고집계시간
            --AND ot.advt <= {{adHours}}
            """.trimIndent()
        )
    }

    @Test
    fun `현재 매출통계에서 사용하지 않는 파라미터가 입력될 시 아무일도 일어나지 않는다`() {
        val 쿼리 = """
            -- 아무것도 없는 쿼리
            -- 아무것도 없는 쿼리
        """.trimIndent()

        val 변경되지_않은_쿼리 = QueryGenerator.generate(
            query = 쿼리, metric = Metric.DAILY.name,
            startDate = LocalDate.now().toString(),
            endDate = LocalDate.now().plusMonths(1).toString(),
            tag = emptyList(),
            salesStart = SalesStart.SHIPPING_REQUEST,
            partnerId = emptyList(),
            category = emptyList(),
            styleNumber = emptyList(),
            goodsNumber = emptyList(),
            brandId = emptyList(),
            couponNumber = emptyList(),
            adCode = emptyList(),
            specialtyCode = emptyList(),
            mdId = emptyList(),
            partnerType = null,
            goodsKind = null,
            salesFunnel = SalesFunnel.DEFAULT,
            adHours = null,
            orderBy = OrderBy.Date.toString(),
            orderDirection = OrderDirection.ASC.toString(),
            pageSize = 100,
            page = 1
        )

        assertThat(변경되지_않은_쿼리).isEqualTo(쿼리.trimIndent())
    }

    @Test
    fun `판매 경로가 기본인 경우 쿼리에서 mobile_yn, app_yn 값이 주석처리 된다`() {
        val 쿼리 = """
            -- 모바일 여부
            AND om.mobile_yn = '{{isMobile}}'

            -- 앱 여부
            AND om.app_yn = '{{isApp}}'
        """.trimIndent()

        val 변경된_쿼리 = QueryGenerator.applySalesFunnel(쿼리, SalesFunnel.DEFAULT)

        assertThat(변경된_쿼리).isEqualTo(
            """
            -- 모바일 여부
            --AND om.mobile_yn = '{{isMobile}}'

            -- 앱 여부
            --AND om.app_yn = '{{isApp}}'
            """.trimIndent()
        )
    }

    @Test
    fun `판매 경로가 모바일 앱인 경우 쿼리에서 mobile_yn Y, app_yn Y 값이 적용된다`() {
        val 쿼리 = """
            -- 모바일 여부
            AND om.mobile_yn = '{{isMobile}}'

            -- 앱 여부
            AND om.app_yn = '{{isApp}}'
        """.trimIndent()

        val 변경된_쿼리 = QueryGenerator.applySalesFunnel(쿼리, SalesFunnel.MOBILE_APP)

        assertThat(변경된_쿼리).isEqualTo(
            """
            -- 모바일 여부
            AND om.mobile_yn = 'Y'

            -- 앱 여부
            AND om.app_yn = 'Y'
            """.trimIndent()
        )
    }

    @Test
    fun `판매 경로가 모바일 웹인 경우 쿼리에서 mobile_yn Y, app_yn N 값이 적용된다`() {
        val 쿼리 = """
            -- 모바일 여부
            AND om.mobile_yn = '{{isMobile}}'

            -- 앱 여부
            AND om.app_yn = '{{isApp}}'
        """.trimIndent()

        val 변경된_쿼리 = QueryGenerator.applySalesFunnel(쿼리, SalesFunnel.MOBILE_WEB)

        assertThat(변경된_쿼리).isEqualTo(
            """
            -- 모바일 여부
            AND om.mobile_yn = 'Y'

            -- 앱 여부
            AND om.app_yn = 'N'
            """.trimIndent()
        )
    }

    @Test
    fun `판매 경로가 모바일 ALL인 경우 쿼리에서 mobile_yn Y 값이 적용 되고, app_yn N 값이 주석처리 된다`() {
        val 쿼리 = """
            -- 모바일 여부
            AND om.mobile_yn = '{{isMobile}}'

            -- 앱 여부
            AND om.app_yn = '{{isApp}}'
        """.trimIndent()

        val 변경된_쿼리 = QueryGenerator.applySalesFunnel(쿼리, SalesFunnel.MOBILE_ALL)

        assertThat(변경된_쿼리).isEqualTo(
            """
            -- 모바일 여부
            AND om.mobile_yn = 'Y'

            -- 앱 여부
            --AND om.app_yn = '{{isApp}}'
            """.trimIndent()
        )
    }
}
