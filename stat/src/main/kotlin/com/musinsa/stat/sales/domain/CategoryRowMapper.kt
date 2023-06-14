package com.musinsa.stat.sales.domain

import com.musinsa.stat.sales.dto.Category
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet

object CategoryRowMapper : RowMapper<Category> {
    override fun mapRow(rs: ResultSet, rowNum: Int): Category {
        return Category(
            rs,
            largeCategoryCode = rs.getString("대분류") ?: null,
            mediumCategoryCode = rs.getString("중분류") ?: null,
            smallCategoryCode = rs.getString("소분류") ?: null,
            category = rs.getString("카테고리명") ?: null
        )
    }
}