package com.musinsa.stat.search.service

import com.musinsa.stat.databricks.service.DatabricksClient
import com.musinsa.stat.search.config.SearchQueryStore
import com.musinsa.stat.search.domain.BrandRowMapper
import com.musinsa.stat.search.dto.Brand
import com.musinsa.stat.search.service.SearchQueryGenerator.replaceBrand
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.jdbc.core.JdbcTemplate
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
        return jdbcTemplate.query(
            replaceBrand(
                databricksClient.getDatabricksQuery(
                    searchQueryStore.brand
                ), searchTerms
            ), BrandRowMapper
        )
    }
}