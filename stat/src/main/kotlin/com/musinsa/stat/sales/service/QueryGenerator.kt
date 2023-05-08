package com.musinsa.stat.sales.service

import com.musinsa.stat.sales.domain.SalesStart

/**
 * 파라미터에 따라서 쿼리를 재생성 한다.
 */
object QueryGenerator {
    val START_DATE = "\\{\\{startDate}}".toRegex()
    val END_DATE = "\\{\\{endDate}}".toRegex()

    /**
     *
     */
    fun generate(
        query: String,
        startDate: String,
        endDate: String,
        tag: List<String>,
        salesStart: SalesStart,
        partnerId: String,
        category: String,
        styleNumber: String,
        goodsNumber: Long,
        brandId: String,
        couponNumber: String,
        adCode: String,
        specialtyCode: String,
        mdId: String
    ) {

    }

    /**
     * 시작날짜와 종료날짜를 추가한다.
     *
     * @param query 원본 쿼리
     *
     * @return 시작날짜와 종료날짜가 추가된 쿼리.
     */
    fun addStarDateAndEndDate(
        query: String,
        startDate: String,
        endDate: String
    ): String {
        return query.replace(START_DATE, startDate)
            .replace(END_DATE, endDate)
    }
}