package com.musinsa.stat.sales.controller

import com.musinsa.stat.sales.domain.Metric
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
    @GetMapping
    fun salesStatistics(
        @PathVariable(required = true) metric: Metric,
        @RequestParam startDate: String,
        @RequestParam endDate: String,
        @RequestParam(required = false) tag: List<String>?,
        @RequestParam salesStart: SalesStart,
        @RequestParam(required = false) partnerId: String?,
        @RequestParam(required = false) category: String?,
        @RequestParam(required = false) styleNumber: String?,
        @RequestParam(required = false) goodsNumber: String?,
        @RequestParam(required = false) brandId: String?,
        @RequestParam(required = false) couponNumber: String?,
        @RequestParam(required = false) adCode: String?,
        @RequestParam(required = false) specialtyCode: String?,
        @RequestParam(required = false) mdId: String?,
        @RequestParam(defaultValue = "date") orderBy: String
    ): SalesStatisticsResponse {
        return when (metric) {
            Metric.DAILY -> salesService.daily(
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
            // TODO 예외처리
            else -> throw Exception("해당 API 없음")
        }
    }
}