package com.musinsa.stat.search.service

import com.musinsa.stat.databricks.service.DatabricksClient
import com.musinsa.stat.sales.dto.Brand
import com.musinsa.stat.search.config.SearchQueryStore
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service

@Service
class SearchService(
    @Qualifier("databricksJdbcTemplate") private val jdbcTemplate: JdbcTemplate,
    private val searchQueryStore: SearchQueryStore,
    private val databricksClient: DatabricksClient
) {
    // TODO 테스트 코드 작성
    /**
     * 브랜드 리스트를 가져온다.
     *
     * @param searchTerms 검색어
     *
     * @return 조회된 브랜드 리스트
     */
    fun getBrands(searchTerms: String): List<Brand> {
        databricksClient.getDatabricksQuery(searchQueryStore.brand)
        return emptyList()
    }
}