package com.musinsa.stat.sales.domain

import com.musinsa.stat.sales.dto.Brand
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet

object BrandRowMapper : RowMapper<Brand> {
    override fun mapRow(rs: ResultSet, rowNum: Int): Brand {
        return Brand(
            rs,
            brandId = rs.getString("브랜드"),
            brandName = rs.getString("브랜드명")
        )
    }
}