package com.musinsa.harrods.databricks.controller

import com.musinsa.harrods.databricks.dto.DatabricksRequest
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.cache.annotation.Cacheable
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/databricks")
class DatabricksController(
    @Qualifier("harrodsDatabricksJdbcTemplate") private val jdbcTemplate: JdbcTemplate
) {

    /**
     * Databricks 쿼리 결과를 조회
     */
    @PostMapping("/queries")
    @Cacheable("databricks")
    fun query(@RequestBody request: DatabricksRequest): List<Map<String, Any>> {
        return jdbcTemplate.queryForList(request.query)
    }
}
