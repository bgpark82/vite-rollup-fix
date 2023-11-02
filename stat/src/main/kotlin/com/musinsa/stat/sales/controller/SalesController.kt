package com.musinsa.stat.sales.controller

import com.musinsa.stat.sales.domain.GoodsKind
import com.musinsa.stat.sales.domain.Metric
import com.musinsa.stat.sales.domain.OrderBy
import com.musinsa.stat.sales.domain.OrderDirection
import com.musinsa.stat.sales.domain.PartnerType
import com.musinsa.stat.sales.domain.SalesFunnel
import com.musinsa.stat.sales.domain.SalesStart
import com.musinsa.stat.sales.dto.SalesStatisticsResponse
import com.musinsa.stat.sales.service.SalesService
import jakarta.validation.constraints.Size
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

const val DATE_FORMAT = "yyyyMMdd"
const val ORDER_DIRECTION_DEFAULT_VALUE = "ASC"
const val PAGE_SIZE_DEFAULT_VALUE = "100000"
const val PAGE_DEFAULT_VALUE = "0"
const val TAG_SIZE_MAX = 100
const val PARTNER_ID_SIZE_MAX = 100
const val CATEGORY_SIZE_MAX = 100
const val STYLE_NUMBER_SIZE_MAX = 100
const val GOODS_NUMBER_SIZE_MAX = 200
const val BRAND_ID_SIZE_MAX = 100
const val COUPON_NUMBER_SIZE_MAX = 200
const val AD_CODE_SIZE_MAX = 200
const val SPECIALTY_CODE_SIZE_MAX = 100
const val MD_ID_SIZE_MAX = 100
const val GOODS_SALES_STATISTICS_VALID_PARAM_CONDITION_DOCS =
    ". 상품별 매출통계의 경우 적어도 하나의 업체 ID, 상품번호, 브랜드 ID, MD, 전문관 코드 값 필요"
const val SALES_FUNNEL_DEFAULT_VALUE = "DEFAULT"

@RestController
@RequestMapping("/sales-statistics")
@Validated
class SalesController(private val salesService: SalesService) {
    /**
     * 일별 매출통계를 가져온다.
     *
     * @param metric 매출통계 유형
     * @param startDate 시작날짜(8자리 yyyyMMdd)
     * @param endDate 종료날짜(8자리 yyyyMMdd)
     * @param tag 태그
     * @param salesStart 매출시점
     * @param partnerId 업체
     * @param category 카테고리
     * @param styleNumber 스타일넘버
     * @param goodsNumber 상품코드
     * @param brandId 브랜드
     * @param couponNumber 쿠폰
     * @param adCode 광고코드
     * @param adHours 광고집계시간
     * @param specialtyCode 전문관코드
     * @param mdId 담당MD
     * @param partnerType 업체 구분
     * @param orderBy 정렬키
     * @param orderDirection 정렬 방향
     * @param pageSize 페이지 사이즈
     * @param page 페이지
     *
     * @return 매출통계 지표
     *
     */
    @GetMapping("/{metric}")
    fun salesStatistics(
        @PathVariable(required = true, value = "metric") metric: Metric,
        @RequestParam(required = true)
        @DateTimeFormat(pattern = DATE_FORMAT)
        startDate: LocalDate,
        @RequestParam(required = true)
        @DateTimeFormat(pattern = DATE_FORMAT)
        endDate: LocalDate,
        @Size(max = TAG_SIZE_MAX)
        @RequestParam(required = false)
        tag: List<String>?,
        @RequestParam(required = true) salesStart: SalesStart,
        @Size(max = PARTNER_ID_SIZE_MAX)
        @RequestParam(required = false)
        partnerId: List<String>?,
        @Size(max = CATEGORY_SIZE_MAX)
        @RequestParam(required = false)
        category: List<String>?,
        @Size(max = STYLE_NUMBER_SIZE_MAX)
        @RequestParam(required = false)
        styleNumber: List<String>?,
        @Size(max = GOODS_NUMBER_SIZE_MAX)
        @RequestParam(required = false)
        goodsNumber: List<String>?,
        @Size(max = BRAND_ID_SIZE_MAX)
        @RequestParam(required = false)
        brandId: List<String>?,
        @Size(max = COUPON_NUMBER_SIZE_MAX)
        @RequestParam(required = false)
        couponNumber: List<String>?,
        @Size(max = AD_CODE_SIZE_MAX)
        @RequestParam(required = false)
        adCode: List<String>?,
        @Size(max = SPECIALTY_CODE_SIZE_MAX)
        @RequestParam(required = false)
        specialtyCode: List<String>?,
        @Size(max = MD_ID_SIZE_MAX)
        @RequestParam(required = false)
        mdId: List<String>?,
        @RequestParam(required = false) partnerType: PartnerType?,
        @RequestParam(required = false) goodsKind: GoodsKind?,
        @RequestParam(
            required = false,
            defaultValue = SALES_FUNNEL_DEFAULT_VALUE
        ) salesFunnel: SalesFunnel,
        @RequestParam(required = false) adHours: Long?,
        @RequestParam(required = true) orderBy: OrderBy,
        @RequestParam(
            required = false,
            defaultValue = ORDER_DIRECTION_DEFAULT_VALUE
        ) orderDirection: OrderDirection,
        @RequestParam(
            required = false,
            defaultValue = PAGE_SIZE_DEFAULT_VALUE
        ) pageSize: Long,
        @RequestParam(
            required = false,
            defaultValue = PAGE_DEFAULT_VALUE
        ) page: Long
    ): SalesStatisticsResponse {
        return when (metric) {
            Metric.GOODS -> salesService.getGoodsSalesStatistics(
                metric,
                startDate,
                endDate,
                tag,
                salesStart,
                partnerId,
                category,
                styleNumber,
                goodsNumber,
                brandId,
                couponNumber,
                adCode,
                specialtyCode,
                mdId,
                partnerType,
                goodsKind,
                salesFunnel,
                adHours,
                orderBy,
                orderDirection,
                pageSize,
                page
            )

            else -> salesService.getSalesStatistics(
                metric,
                startDate,
                endDate,
                tag,
                salesStart,
                partnerId,
                category,
                styleNumber,
                goodsNumber,
                brandId,
                couponNumber,
                adCode,
                specialtyCode,
                mdId,
                partnerType,
                goodsKind,
                salesFunnel,
                adHours,
                orderBy,
                orderDirection,
                pageSize,
                page
            )
        }
    }
}
