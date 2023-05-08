package com.musinsa.stat.sales.service

import com.musinsa.stat.sales.domain.SalesStart

/**
 * 파라미터에 따라서 쿼리를 재생성 한다.
 */
object QueryGenerator {
    val START_DATE = "\\{\\{startDate}}".toRegex()
    val END_DATE = "\\{\\{endDate}}".toRegex()
    val TAG = "\\{\\{tag}}".toRegex()

    /**
     * @param startDate 시작날짜
     * @param endDate 종료날짜
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
     *
     * @return 치환된 쿼리
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
    ): String {

        return ""
    }

    fun addStarDateAndEndDate(
        query: String,
        startDate: String,
        endDate: String
    ): String {
        return query.replace(START_DATE, startDate)
            .replace(END_DATE, endDate)
    }

    fun addTag(query: String, tag: List<String>): String {
        if (tag.isEmpty()) {
            // TODO tag 주석 처리
        }

        return query.replace(
            TAG,
            tag.joinToString(
                separator = "', '",
                prefix = "'",
                postfix = "'"
            )
        )
    }
}