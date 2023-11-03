package com.musinsa.stat.sales.domain

import com.musinsa.stat.sales.dto.Category
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet

const val NULL_VALUE = "-"

object CategoryRowMapper : RowMapper<Category> {
    override fun mapRow(rs: ResultSet, rowNum: Int): Category {
        return Category(
            rs,
            largeCategoryCode = rs.getString("대분류") ?: NULL_VALUE,
            mediumCategoryCode = rs.getString("중분류") ?: NULL_VALUE,
            smallCategoryCode = rs.getString("소분류") ?: NULL_VALUE,
            largeCategory = rs.getString("대분류_카테고리명") ?: NULL_VALUE,
            mediumCategory = rs.getString("중분류_카테고리명") ?: NULL_VALUE,
            smallCategory = rs.getString("소분류_카테고리명") ?: NULL_VALUE
        )
    }
}
