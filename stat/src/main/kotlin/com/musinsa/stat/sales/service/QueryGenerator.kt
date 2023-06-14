package com.musinsa.stat.sales.service

import com.musinsa.stat.sales.domain.SalesStart
import com.musinsa.stat.sales.error.SalesError

/**
 * 파라미터에 따라서 쿼리를 재생성 한다.
 */
object QueryGenerator {
    private const val PREFIX_ANNOTATION = "--"
    private val START_DATE = "\\{\\{startDate}}".toRegex()
    private val END_DATE = "\\{\\{endDate}}".toRegex()
    private val TAG = "\\{\\{tag}}".toRegex()
    private val SALES_START = "\\{\\{salesStart}}".toRegex()
    private val PARTNER_ID = "\\{\\{partnerId}}".toRegex()
    private val CATEGORY = "\\{\\{category}}".toRegex()
    private val STYLE_NUMBER = "\\{\\{styleNumber}}".toRegex()
    private val GOODS_NUMBER = "\\{\\{goodsNumber}}".toRegex()
    private val BRAND_ID = "\\{\\{brandId}}".toRegex()
    private val COUPON_NUMBER = "\\{\\{couponNumber}}".toRegex()
    private val AD_CODE = "\\{\\{adCode}}".toRegex()
    private val SPECIALTY_CODE = "\\{\\{specialtyCode}}".toRegex()
    private val MD_ID = "\\{\\{mdId}}".toRegex()
    private val ORDER_BY = "\\{\\{orderBy}}".toRegex()
    private const val JOIN_GOODS_TAGS = "{{joinGoodsTags}}"
    private val JOIN_COUPON = "{{joinCoupon}}"
    private val JOIN_SPECIALTY_GOODS = "{{joinSpecialtyGoods}}"

    /**
     * 배열에서 특정 문자열이 속한 index 를 찾는다.
     *
     * @param array 배열
     * @param target 찾을 문자열
     *
     * @return target 이 속한 배열
     */
    fun getStringLineNumber(array: ArrayList<String>, target: String): Int {
        val index =
            array.indexOfFirst { str -> str.contains(target) }
        return when (index >= 0) {
            true -> index
            false -> SalesError.UNKNOWN_SEARCH_PARAM.throwMe()
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
     * JOIN 필요한 경우, 주석 처리 된 것을 지운다.
     *
     * @param query 쿼리
     * @param target 주석을 제거할 문자열
     *
     * @return FROM 에서 JOIN 구문을 되살린 쿼리
     */
    fun removeAnnotationFromPhrase(query: String, target: String): String {
        val array = query.lines() as ArrayList<String>
        val index = getStringLineNumber(array, target)
        array[index] = array[index].replace(PREFIX_ANNOTATION.plus(target), "")
        return array.joinToString(separator = "\n").trimIndent()
    }

    /**
     * 값이 비어있지 않으면 쿼리에 추가한다.
     * 값이 빈 경우, 주석처리한다.
     *
     * @param query 쿼리
     * @param target 주석처리할 정규표현식
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
     *
     * @return 치환된 쿼리
     *
     */
    fun generate(
        query: String,
        startDate: String,
        endDate: String,
        tag: List<String>?,
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
        orderBy: String
    ): String {
        return applyOrderKey(
            applyMdIdOrAnnotate(
                applySpecialtyCodeOrAnnotate(
                    applyAdCodeOrAnnotate(
                        applyCouponNumberOrAnnotate(
                            applyBrandIdOrAnnotate(
                                applyGoodsNumberOrAnnotate(
                                    applyStyleNumberOrAnnotate(
                                        applyCategoryOrAnnotate(
                                            applyPartnerIdOrAnnotate(
                                                applySalesStart(
                                                    applyTagOrAnnotate(
                                                        applyStarDateAndEndDate(
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
            ), orderBy
        )
    }

    /**
     * 시작 날짜와 종료 날짜 추가
     */
    fun applyStarDateAndEndDate(
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
    fun applyTagOrAnnotate(query: String, tag: List<String>?): String {
        if (tag.isNullOrEmpty()) return replaceQueryOrSetAnnotation(
            query,
            TAG,
            String()
        )

        return removeAnnotationFromPhrase(
            query.replace(
                TAG,
                tag.joinToString(
                    separator = "', '",
                    prefix = "'",
                    postfix = "'"
                )
            ), JOIN_GOODS_TAGS
        )
    }

    /**
     * 매출시점 추가
     */
    fun applySalesStart(query: String, salesStart: SalesStart): String {
        return query.replace(SALES_START, salesStart.toString())
    }

    /**
     * 업체 추가
     */
    fun applyPartnerIdOrAnnotate(query: String, partnerId: String?): String {
        return replaceQueryOrSetAnnotation(query, PARTNER_ID, partnerId)
    }

    /**
     * 카테고리 추가
     */
    fun applyCategoryOrAnnotate(query: String, category: String?): String {
        return replaceQueryOrSetAnnotation(query, CATEGORY, category)
    }

    /**
     * 스타일넘버 추가
     */
    fun applyStyleNumberOrAnnotate(
        query: String,
        styleNumber: String?
    ): String {
        return replaceQueryOrSetAnnotation(query, STYLE_NUMBER, styleNumber)
    }

    /**
     * 상품코드 추가
     */
    fun applyGoodsNumberOrAnnotate(
        query: String,
        goodsNumber: String?
    ): String {
        return replaceQueryOrSetAnnotation(query, GOODS_NUMBER, goodsNumber)
    }

    /**
     * 브랜드 추가
     */
    fun applyBrandIdOrAnnotate(query: String, brandId: String?): String {
        return replaceQueryOrSetAnnotation(query, BRAND_ID, brandId)
    }

    /**
     * 쿠폰 추가
     */
    fun applyCouponNumberOrAnnotate(
        query: String,
        couponNumber: String?
    ): String {
        return replaceQueryOrSetAnnotation(query, COUPON_NUMBER, couponNumber)
    }

    /**
     * 광고코드 추가
     */
    fun applyAdCodeOrAnnotate(query: String, adCode: String?): String {
        return replaceQueryOrSetAnnotation(query, AD_CODE, adCode)
    }

    /**
     * 전문관코드 추가
     */
    fun applySpecialtyCodeOrAnnotate(
        query: String,
        specialtyCode: String?
    ): String {
        return replaceQueryOrSetAnnotation(query, SPECIALTY_CODE, specialtyCode)
    }

    /**
     * 담당MD 추가
     */
    fun applyMdIdOrAnnotate(query: String, mdId: String?): String {
        return replaceQueryOrSetAnnotation(query, MD_ID, mdId)
    }

    /**
     * 정렬키 추가
     */
    fun applyOrderKey(
        query: String,
        orderBy: String
    ): String {
        return query.replace(ORDER_BY, orderBy)
    }
}