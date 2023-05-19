package com.musinsa.stat.sales.controller

import com.musinsa.stat.sales.domain.Metric
import com.musinsa.stat.sales.domain.OrderBy
import com.musinsa.stat.sales.domain.SalesStart
import com.musinsa.stat.sales.dto.SalesStatisticsResponse
import com.musinsa.stat.sales.service.SalesService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/sales-statistics")
class SalesController(private val salesService: SalesService) {
    // TODO 유효성 체크 + 조회 구간 한정(1년)
    // TODO 테스트코드 추가
    // TODO REST Docs 추가

    /**
     * 일별 매출통계를 가져온다.
     *
     * @param metric 매출통계 유형
     * @param startDate 시작날짜
     * @param endDate 종료날짜
     * @param tag 태그. 기본값: 빈배열
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
     *
     * @return 매출통계 지표
     *
     */
    @GetMapping("/{metric}")
    fun salesStatistics(
        @PathVariable(required = true, value = "metric") metric: Metric,
        @RequestParam(required = true) startDate: String,
        @RequestParam(required = true) endDate: String,
        @RequestParam(required = false) tag: List<String>?,
        @RequestParam(required = true) salesStart: SalesStart,
        @RequestParam(required = false) partnerId: String?,
        @RequestParam(required = false) category: String?,
        @RequestParam(required = false) styleNumber: String?,
        @RequestParam(required = false) goodsNumber: String?,
        @RequestParam(required = false) brandId: String?,
        @RequestParam(required = false) couponNumber: String?,
        @RequestParam(required = false) adCode: String?,
        @RequestParam(required = false) specialtyCode: String?,
        @RequestParam(required = false) mdId: String?,
        @RequestParam(required = true) orderBy: OrderBy
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
            orderBy
        )
    }
}