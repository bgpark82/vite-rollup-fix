package com.musinsa.stat.search.service

/**
 * 검색 쿼리를 재생성 한다.
 */
object SearchQueryGenerator {
    private val BRAND_ID = "\\{\\{brandId}}".toRegex()
    private val BRAND_NAME = "\\{\\{brandName}}".toRegex()
    private val PARTNER_ID = "\\{\\{partnerId}}".toRegex()
    private val PARTNER_NAME = "\\{\\{partnerName}}".toRegex()

    /**
     * 브랜드를 검색 쿼리 예약어에 추가한다.
     */
    fun replaceBrand(query: String, searchTerms: String): String {
        return query.replace(BRAND_ID, searchTerms)
            .replace(BRAND_NAME, searchTerms)
    }

    /**
     * 업체를 검색 쿼리 예약어에 추가한다.
     */
    fun replacePartner(query: String, searchTerms: String): String {
        return query.replace(PARTNER_ID, searchTerms)
            .replace(PARTNER_NAME, searchTerms)
    }
}