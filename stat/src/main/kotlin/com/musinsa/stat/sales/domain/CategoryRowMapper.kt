package com.musinsa.stat.sales.domain

import com.musinsa.stat.sales.dto.Category
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet

object CategoryRowMapper : RowMapper<Category> {
    private const val NULL_VALUE = "값없음"
    override fun mapRow(rs: ResultSet, rowNum: Int): Category {
        return Category(
            rs,
            largeCategoryCode = rs.getString("대분류") ?: NULL_VALUE,
            mediumCategoryCode = rs.getString("중분류") ?: NULL_VALUE,
            smallCategoryCode = rs.getString("소분류") ?: NULL_VALUE,
            category = rs.getString("카테고리명") ?: NULL_VALUE
        )
    }
}