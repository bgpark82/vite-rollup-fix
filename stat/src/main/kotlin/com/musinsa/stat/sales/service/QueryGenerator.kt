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
    val SALES_START = "\\{\\{salesStart}}".toRegex()
    val PARTNER_ID = "\\{\\{partnerId}}".toRegex()
    val CATEGORY = "\\{\\{category}}".toRegex()
    val STYLE_NUMBER = "\\{\\{styleNumber}}".toRegex()
    val GOODS_NUMBER = "\\{\\{goodsNumber}}".toRegex()
    val BRAND_ID = "\\{\\{brandId}}".toRegex()
    val COUPON_NUMBER = "\\{\\{couponNumber}}".toRegex()
    val AD_CODE = "\\{\\{adCode}}".toRegex()
    val SPECIALTY_CODE = "\\{\\{specialtyCode}}".toRegex()
    val MD_ID = "\\{\\{mdId}}".toRegex()
    val ORDER_BY = "\\{\\{orderBy}}".toRegex()
    val SIZE = "\\{\\{size}}".toRegex()
    val NUMBER = "\\{\\{number}}".toRegex()

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
     * 값이 비어있지 않으면 쿼리에 추가한다.
     * 값이 빈 경우, 주석처리한다.
     *
     * @param query 쿼리
     * @param target 주석처리할 옵션
     * @param value 치환할 값
     *
     * @return 처리된 쿼리
     */
    private fun replaceQueryOrSetAnnotation(
        query: String,
        target: Regex,
        value: String?
    ): String {
        if (value.isNullOrBlank()) {
            val array = query.lines() as ArrayList<String>
            return annotateUnusedWhereCondition(
                array,
                getStringLineNumber(array, target.toString().replace("\\", ""))
            )
        }
        return query.replace(target, value)
    }

    /**
     * @param startDate 시작날짜
     * @param endDate 종료날짜
     * @param tag 태그
     * @param salesStart 매출시점
     * @param partnerId 업체
     * @param category 카테고리
     * @param styleNumber 스타일넘버
     * @param goodsNumber 상품코드
     * @param brandId 브랜드
     * @param couponNumber 쿠폰
     * @param adCode 광고코드
     * @param specialtyCode 전문관코드
     * @param mdId 담당MD
     * @param orderBy 정렬키
     * @param size 페이징 시 가져올 원소 수
     * @param number 가져올 페이지
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
        partnerId: String?,
        category: String?,
        styleNumber: String?,
        goodsNumber: String?,
        brandId: String?,
        couponNumber: String?,
        adCode: String?,
        specialtyCode: String?,
        mdId: String?,
        orderBy: String,
        size: Long,
        number: Long
    ): String {
        return addPagingParams(
            addMdId(
                addSpecialtyCode(
                    addAdCode(
                        addCouponNumber(
                            addBrandId(
                                addGoodsNumber(
                                    addStyleNumber(
                                        addCategory(
                                            addPartnerId(
                                                addSalesStart(
                                                    addTag(
                                                        addStarDateAndEndDate(
                                                            query,
                                                            startDate,
                                                            endDate
                                                        ),
                                                        tag
                                                    ), salesStart
                                                ), partnerId
                                            ), category
                                        ), styleNumber
                                    ), goodsNumber
                                ), brandId
                            ), couponNumber
                        ), adCode
                    ), specialtyCode
                ), mdId
            ), orderBy, size, number
        )
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
            return replaceQueryOrSetAnnotation(
                query,
                TAG,
                String()
            )

        return query.replace(
            TAG,
            tag.joinToString(
                separator = "', '",
                prefix = "'",
                postfix = "'"
            )
        )
    }

    /**
     * 매출시점 추가
     */
    fun addSalesStart(query: String, salesStart: SalesStart): String {
        return query.replace(SALES_START, salesStart.toString())
    }

    /**
     * 업체 추가
     */
    fun addPartnerId(query: String, partnerId: String?): String {
        return replaceQueryOrSetAnnotation(query, PARTNER_ID, partnerId)
    }

    /**
     * 카테고리 추가
     */
    fun addCategory(query: String, category: String?): String {
        return replaceQueryOrSetAnnotation(query, CATEGORY, category)
    }

    /**
     * 스타일넘버 추가
     */
    fun addStyleNumber(query: String, styleNumber: String?): String {
        return replaceQueryOrSetAnnotation(query, STYLE_NUMBER, styleNumber)
    }

    /**
     * 상품코드 추가
     */
    fun addGoodsNumber(query: String, goodsNumber: String?): String {
        return replaceQueryOrSetAnnotation(query, GOODS_NUMBER, goodsNumber)
    }

    /**
     * 브랜드 추가
     */
    fun addBrandId(query: String, brandId: String?): String {
        return replaceQueryOrSetAnnotation(query, BRAND_ID, brandId)
    }

    /**
     * 쿠폰 추가
     */
    fun addCouponNumber(query: String, couponNumber: String?): String {
        return replaceQueryOrSetAnnotation(query, COUPON_NUMBER, couponNumber)
    }

    /**
     * 광고코드 추가
     */
    fun addAdCode(query: String, adCode: String?): String {
        return replaceQueryOrSetAnnotation(query, AD_CODE, adCode)
    }

    /**
     * 전문관코드 추가
     */
    fun addSpecialtyCode(query: String, specialtyCode: String?): String {
        return replaceQueryOrSetAnnotation(query, SPECIALTY_CODE, specialtyCode)
    }

    /**
     * 담당MD 추가
     */
    fun addMdId(query: String, mdId: String?): String {
        return replaceQueryOrSetAnnotation(query, MD_ID, mdId)
    }

    /**
     * 정렬키, 페이지 원소수, 페이지 번호 추가
     */
    fun addPagingParams(
        query: String,
        orderBy: String,
        size: Long,
        number: Long
    ): String {
        return query.replace(ORDER_BY, orderBy)
            .replace(SIZE, size.toString()).replace(NUMBER, number.toString())
    }
}