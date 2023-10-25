package com.musinsa.stat.sales.service

import com.musinsa.stat.sales.domain.Metric
import com.musinsa.stat.sales.domain.SalesStart
import com.musinsa.stat.sales.error.SalesError

const val PREFIX_ANNOTATION = "--"

/**
 * 파라미터에 따라서 쿼리를 재생성 한다.
 */
object QueryGenerator {
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
    private val PARTNER_TYPE = "\\{\\{partnerType}}".toRegex()
    private val GOODS_KIND = "\\{\\{goodsKind}}".toRegex()
    private val ORDER_BY = "\\{\\{orderBy}}".toRegex()
    private val ORDER_DIRECTION = "\\{\\{orderDirection}}".toRegex()
    private val PAGE_SIZE = "\\{\\{pageSize}}".toRegex()
    private val PAGE = "\\{\\{page}}".toRegex()
    private const val JOIN_GOODS_TAGS = "{{joinGoodsTags}}"
    private const val JOIN_COUPON = "{{joinCoupon}}"
    private const val JOIN_SPECIALTY_GOODS = "{{joinSpecialtyGoods}}"

    /**
     * 배열에서 특정 문자열이 속한 index를 찾는다.
     *
     * @param array 배열
     * @param target 찾을 문자열
     *
     * @return target 이 속한 배열
     */
    fun getStringLineNumber(
        array: ArrayList<String>,
        target: String
    ): Int {
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
     * SQL FROM 절에서 JOIN 을 통해 테이블을 가져와야 할 필요가 있는 파라미터에서 사용
     * params 값이 존재하면, JOIN 주석을 해제하고 WHERE 파라미터로 입력한다.
     * 그렇지 않은 경우, JOIN 주석을 유지하고, WHERE 파라미터를 주석 처리한다.
     *
     * @param query 쿼리
     * @param joinTarget 주석을 제거할 FROM 절 Line
     * @param params WHERE 조건
     * @param whereTarget WHERE 주석 타겟
     *
     * @return 계산된 쿼리
     */
    private fun removeAnnotationSQLFromPhraseOrKeepAnnotation(
        query: String,
        joinTarget: String,
        params: List<String>?,
        whereTarget: Regex
    ): String {
        // 값이 없는 경우
        if (params.isNullOrEmpty()) {
            val array = query.lines() as ArrayList<String>
            return annotateUnusedWhereCondition(
                array,
                getStringLineNumber(
                    array,
                    whereTarget.toString().replace("\\", "")
                )
            )
        }

        val array = replaceParamListToSQLInParam(
            query,
            whereTarget,
            params
        ).lines() as ArrayList<String>
        val index = getStringLineNumber(array, joinTarget)
        array[index] =
            array[index].replace(PREFIX_ANNOTATION.plus(joinTarget), "")
        return array.joinToString(separator = "\n").trimIndent()
    }

    /**
     * 리스트 형태의 파라미터가 비었다면 WHERE IN 절 주석처리를 하고, 그렇지 않으면 값을 설정한다.
     *
     * @param query 원본 쿼리
     * @param target 변환할 정규표현식
     * @param params 리스트 형태의 파라미터
     *
     * @return 주석처리 혹은 WHERE IN 형태를 적용한 쿼리
     */
    private fun replaceParamListOrAnnotate(
        query: String,
        target: Regex,
        params: List<String>?
    ): String {
        if (params.isNullOrEmpty()) {
            val array = query.lines() as ArrayList<String>
            return annotateUnusedWhereCondition(
                array,
                getStringLineNumber(array, target.toString().replace("\\", ""))
            )
        }
        return replaceParamListToSQLInParam(query, target, params)
    }

    /**
     * 파라미터가 비었다면 WHERE 절 주석처리를 하고, 그렇지 않으면 값을 설정한다.
     *
     * @param query 원본 쿼리
     * @param target 변환할 정규표현식
     * @param params 파라미터
     *
     * @return 주석처리 혹은 WHERE 형태를 적용한 쿼리
     */
    private fun replaceParamOrAnnotate(
        query: String,
        target: Regex,
        params: String?
    ): String {
        if (params.isNullOrBlank()) {
            val array = query.lines() as ArrayList<String>
            return annotateUnusedWhereCondition(
                array,
                getStringLineNumber(array, target.toString().replace("\\", ""))
            )
        }
        return query.replace(target, params)
    }

    /**
     * 리스트형태의 파라미터를 WHERE IN 절에 맞도록 변환시킨다.
     *
     * @param query 원본 쿼리
     * @param target 변환할 정규표현식
     * @param params 리스트 형태의 파라미터
     *
     * @return WHERE IN 형태를 적용한 쿼리
     *
     */
    private fun replaceParamListToSQLInParam(
        query: String,
        target: Regex,
        params: List<String>
    ): String {
        return query.replace(
            target,
            params.joinToString(
                separator = "', '",
                prefix = "'",
                postfix = "'"
            )
        )
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
     * @param partnerType 업체 구분
     * @param orderBy 정렬키
     * @param metric 매출통계 유형
     * @param orderDirection 정렬 방향
     * @param pageSize 페이지 사이즈
     * @param page 페이지
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
        partnerId: List<String>?,
        category: List<String>?,
        styleNumber: List<String>?,
        goodsNumber: List<String>?,
        brandId: List<String>?,
        couponNumber: List<String>?,
        adCode: List<String>?,
        specialtyCode: List<String>?,
        mdId: List<String>?,
        partnerType: String?,
        orderBy: String,
        metric: Metric,
        orderDirection: String,
        pageSize: Long,
        page: Long
    ): String {
        return applyPagingParams(
            applyPartnerType(
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
                                                        ),
                                                        salesStart
                                                    ),
                                                    partnerId
                                                ),
                                                category
                                            ),
                                            styleNumber
                                        ),
                                        goodsNumber
                                    ),
                                    brandId
                                ),
                                couponNumber,
                                metric
                            ),
                            adCode
                        ),
                        specialtyCode
                    ),
                    mdId
                ),
                partnerType
            ),
            orderBy,
            orderDirection,
            pageSize,
            page
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
        return removeAnnotationSQLFromPhraseOrKeepAnnotation(
            query,
            JOIN_GOODS_TAGS,
            tag,
            TAG
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
    fun applyPartnerIdOrAnnotate(
        query: String,
        partnerId: List<String>?
    ): String {
        return replaceParamListOrAnnotate(query, PARTNER_ID, partnerId)
    }

    /**
     * 카테고리 추가
     */
    fun applyCategoryOrAnnotate(
        query: String,
        category: List<String>?
    ): String {
        return replaceParamListOrAnnotate(query, CATEGORY, category)
    }

    /**
     * 스타일넘버 추가
     */
    fun applyStyleNumberOrAnnotate(
        query: String,
        styleNumber: List<String>?
    ): String {
        return replaceParamListOrAnnotate(query, STYLE_NUMBER, styleNumber)
    }

    /**
     * 상품코드 추가
     */
    fun applyGoodsNumberOrAnnotate(
        query: String,
        goodsNumber: List<String>?
    ): String {
        return replaceParamListOrAnnotate(query, GOODS_NUMBER, goodsNumber)
    }

    /**
     * 브랜드 추가
     */
    fun applyBrandIdOrAnnotate(query: String, brandId: List<String>?): String {
        return replaceParamListOrAnnotate(query, BRAND_ID, brandId)
    }

    /**
     * 쿠폰 추가
     */
    fun applyCouponNumberOrAnnotate(
        query: String,
        couponNumber: List<String>?,
        metric: Metric
    ): String {
        // 쿠폰별 매출통계는 이미 JOIN 이 적용 되어 있으므로, WHERE 절 주석 여부만 확인
        if (metric == Metric.COUPON) {
            return replaceParamListOrAnnotate(
                query,
                COUPON_NUMBER,
                couponNumber
            )
        }

        return removeAnnotationSQLFromPhraseOrKeepAnnotation(
            query,
            JOIN_COUPON,
            couponNumber,
            COUPON_NUMBER
        )
    }

    /**
     * 광고코드 추가
     */
    fun applyAdCodeOrAnnotate(query: String, adCode: List<String>?): String {
        return replaceParamListOrAnnotate(query, AD_CODE, adCode)
    }

    /**
     * 전문관코드 추가
     */
    fun applySpecialtyCodeOrAnnotate(
        query: String,
        specialtyCode: List<String>?
    ): String {
        return removeAnnotationSQLFromPhraseOrKeepAnnotation(
            query,
            JOIN_SPECIALTY_GOODS,
            specialtyCode,
            SPECIALTY_CODE
        )
    }

    /**
     * 담당MD 추가
     */
    fun applyMdIdOrAnnotate(query: String, mdId: List<String>?): String {
        return replaceParamListOrAnnotate(query, MD_ID, mdId)
    }

    /**
     * 페이징 파라미터 추가
     */
    fun applyPagingParams(
        query: String,
        orderBy: String,
        orderDirection: String,
        pageSize: Long,
        page: Long
    ): String {
        return query.replace(ORDER_BY, orderBy)
            .replace(ORDER_DIRECTION, orderDirection).replace(
                PAGE_SIZE,
                pageSize.toString()
            ).replace(PAGE, page.toString())
    }

    /**
     * 업체 구분 추가
     */
    fun applyPartnerType(query: String, partnerType: String?): String {
        return replaceParamOrAnnotate(query, PARTNER_TYPE, partnerType)
    }

    /**
     * 품목(상품 옵션) 추가
     */
    fun applyGoodsKind(query: String, goodsKind: String?): String {
        return replaceParamOrAnnotate(query, GOODS_KIND, goodsKind)
    }
}
