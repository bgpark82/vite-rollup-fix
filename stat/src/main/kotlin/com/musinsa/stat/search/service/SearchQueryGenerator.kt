package com.musinsa.stat.search.service

import com.musinsa.stat.search.domain.SearchType

/**
 * 검색 쿼리를 재생성 한다.
 */
object SearchQueryGenerator {
    private val BRAND_ID = "\\{\\{brandId}}".toRegex()
    private val BRAND_NAME = "\\{\\{brandName}}".toRegex()
    private val PARTNER_ID = "\\{\\{partnerId}}".toRegex()
    private val PARTNER_NAME = "\\{\\{partnerName}}".toRegex()
    private val TAG = "\\{\\{tag}}".toRegex()

    /**
     * 브랜드를 검색 쿼리 예약어에 추가한다.
     */
    private fun replaceBrand(query: String, searchTerms: String): String {
        return query.replace(BRAND_ID, searchTerms)
            .replace(BRAND_NAME, searchTerms)
    }

    /**
     * 업체를 검색 쿼리 예약어에 추가한다.
     */
    private fun replacePartner(query: String, searchTerms: String): String {
        return query.replace(PARTNER_ID, searchTerms)
            .replace(PARTNER_NAME, searchTerms)
    }

    /**
     * 태그를 검색 쿼리 예약어에 추가한다.
     */
    private fun replaceTag(query: String, searchTerms: String): String {
        return query.replace(TAG, searchTerms)
    }

    /**
     * 검색 유형에 맞게 쿼리를 치환한다.
     */
    fun replace(
        query: String,
        searchTerms: String,
        searchType: SearchType
    ): String {
        return when (searchType) {
            SearchType.BRAND -> replaceBrand(query, searchTerms)
            SearchType.PARTNER -> replacePartner(query, searchTerms)
            SearchType.TAG -> replaceTag(query, searchTerms)
        }
    }
}
