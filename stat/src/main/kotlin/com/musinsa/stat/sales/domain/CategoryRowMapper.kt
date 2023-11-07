package com.musinsa.stat.sales.domain

import com.musinsa.stat.sales.dto.Category
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet

object CategoryRowMapper : RowMapper<Category> {
    override fun mapRow(rs: ResultSet, rowNum: Int): Category {
        return Category(
            rs,
            largeCategoryCode = rs.getString("대분류"),
            mediumCategoryCode = rs.getString("중분류"),
            smallCategoryCode = rs.getString("소분류"),
            largeCategory = rs.getString("대분류_카테고리명"),
            mediumCategory = rs.getString("중분류_카테고리명"),
            smallCategory = rs.getString("소분류_카테고리명")
        )
    }
}
