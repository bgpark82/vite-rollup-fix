package com.musinsa.stat.sales.service

import com.musinsa.stat.sales.domain.SalesStart

/**
 * 파라미터에 따라서 쿼리를 재생성 한다.
 */
object QueryGenerator {
    val PREFIX_ANNOTATION = "--"
    val START_DATE = "\\{\\{startDate}}".toRegex()
    val END_DATE = "\\{\\{endDate}}".toRegex()
    val TAG = "\\{\\{tag}}".toRegex()

    /**
     * 배열에서 특정 문자열이 속한 index를 찾는다.
     *
     * @param array 배열
     * @param target 찾을 문자열
     *
     * @return target이 속한 배열
     */
    fun getStringLineNumber(array: ArrayList<String>, target: String): Int {
        val index =
            array.indexOfFirst { str -> str.contains(target) }
        return when (index >= 0) {
            true -> index
            // TODO 예외처리 통일
            false -> throw Exception("문자열: " + target + " 을 찾을 수 없음")
        }
    }

    /**
     * 사용하지 않는 조건을 주석처리한다.
     *
     * @param array 배열처리된 쿼리
     * @param index 주석처리할 SQL 라인
     *
     * @return 주석처리된 쿼리
     */
    fun annotateUnusedWhereCondition(
        array: ArrayList<String>,
        index: Int
    ): String {
        array[index] =
            StringBuilder().append(PREFIX_ANNOTATION).append(array[index])
                .toString()
        return array.joinToString(separator = "\n").trimIndent()
    }

    /**
     * 사용하지 않는 주석 제거
     *
     * @param query 쿼리
     * @param target 주석처리할 옵션
     *
     * @return 주석처리된 쿼리
     */
    fun annotateOption(query: String, target: Regex): String {
        val array = query.lines() as ArrayList<String>
        return annotateUnusedWhereCondition(
            array,
            getStringLineNumber(array, target.toString().replace("\\", ""))
        )
    }

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

    /**
     * 시작 날짜와 종료 날짜 추가
     */
    fun addStarDateAndEndDate(
        query: String,
        startDate: String,
        endDate: String
    ): String {
        return query.replace(START_DATE, startDate)
            .replace(END_DATE, endDate)
    }

    /**
     * 태그 추가
     */
    fun addTag(query: String, tag: List<String>): String {
        if (tag.isEmpty())
            return annotateOption(query, TAG)

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