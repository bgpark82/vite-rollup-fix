package com.musinsa.stat.search.service

import com.musinsa.stat.databricks.service.DatabricksClient
import com.musinsa.stat.search.config.SearchQueryStore
import com.musinsa.stat.search.domain.BrandRowMapper
import com.musinsa.stat.search.domain.PartnerRowMapper
import com.musinsa.stat.search.domain.SearchType
import com.musinsa.stat.search.domain.TagRowMapper
import com.musinsa.stat.search.dto.Brand
import com.musinsa.stat.search.dto.Partner
import com.musinsa.stat.search.dto.Tag
import com.musinsa.stat.search.service.SearchQueryGenerator.replace
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Service

@Service
class SearchService(
    @Qualifier("databricksJdbcTemplate") private val jdbcTemplate: JdbcTemplate,
    private val searchQueryStore: SearchQueryStore,
    private val databricksClient: DatabricksClient
) {
    /**
     * 브랜드 리스트를 가져온다.
     *
     * @param searchTerms 검색어
     *
     * @return 조회된 브랜드 리스트
     */
    fun getBrands(searchTerms: String): List<Brand> {
        return getSearchResults(
            searchTerms,
            searchQueryStore.brand,
            BrandRowMapper,
            SearchType.BRAND
        )
    }

    /**
     * 업체 리스트를 가져온다.
     *
     * @param searchTerms 검색어
     *
     * @return 조회된 업체 리스트
     */
    fun getPartners(searchTerms: String): List<Partner> {
        return getSearchResults(
            searchTerms,
            searchQueryStore.partner,
            PartnerRowMapper,
            SearchType.PARTNER
        )
    }

    /**
     * 태그 리스트를 가져온다.
     *
     * @param searchTerms 검색어
     *
     * @return 조회된 업체 리스트
     */
    fun getTags(searchTerms: String): List<Tag> {
        return getSearchResults(
            searchTerms,
            searchQueryStore.tag,
            TagRowMapper,
            SearchType.TAG
        )
    }

    /**
     * 검색 쿼리를 실행 시킨 후, 결과를 가져온다.
     *
     * @param searchTerms 검색어
     * @param searchQueryUrl 검색 쿼리 주소
     * @param rowMapper 변환 클래스 유형
     *
     * @return 질의 쿼리 실행 후, 검색 결과
     */
    private fun <T> getSearchResults(
        searchTerms: String,
        searchQueryUrl: String,
        rowMapper: RowMapper<T>,
        searchType: SearchType
    ): List<T> {
        return jdbcTemplate.query(
            replace(
                databricksClient.getDatabricksQuery(
                    searchQueryUrl
                ), searchTerms, searchType
            ), rowMapper
        )
    }
}