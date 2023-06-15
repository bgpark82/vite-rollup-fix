package com.musinsa.stat.sales.controller

import com.musinsa.stat.sales.domain.Metric
import com.musinsa.stat.sales.domain.OrderBy
import com.musinsa.stat.sales.domain.OrderDirection
import com.musinsa.stat.sales.domain.SalesStart
import com.musinsa.stat.sales.dto.SalesStatisticsResponse
import com.musinsa.stat.sales.service.SalesService
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

const val DATE_FORMAT = "yyyyMMdd"
const val ORDER_DIRECTION_DEFAULT_VALUE = "ASC"
const val PAGE_SIZE_DEFAULT_VALUE = "100000"
const val PAGE_DEFAULT_VALUE = "0"

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
     * @param category
     * @param styleNumber 스타일넘버
     * @param goodsNumber 상품코드
     * @param brandId 브랜드
     * @param couponNumber 쿠폰
     * @param adCode 광고코드
     * @param specialtyCode 전문관코드
     * @param mdId 담당MD
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
        @RequestParam(required = true) @DateTimeFormat(pattern = DATE_FORMAT) startDate: LocalDate,
        @RequestParam(required = true) @DateTimeFormat(pattern = DATE_FORMAT) endDate: LocalDate,
        @RequestParam(required = false) tag: List<String>?,
        @RequestParam(required = true) salesStart: SalesStart,
        @RequestParam(required = false) partnerId: String?,
        @RequestParam(required = false) category: String?,
        @RequestParam(required = false) styleNumber: String?,
        @RequestParam(required = false) goodsNumber: String?,
        @RequestParam(required = false) brandId: String?,
        @RequestParam(required = false) couponNumber: List<String>?,
        @RequestParam(required = false) adCode: String?,
        @RequestParam(required = false) specialtyCode: List<String>?,
        @RequestParam(required = false) mdId: String?,
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
        ) page: Long,
    ): SalesStatisticsResponse {
        return salesService.getSalesStatistics(
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
            orderBy,
            orderDirection,
            pageSize,
            page
        )
    }
}