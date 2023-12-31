package com.musinsa.stat.search.domain

import com.musinsa.stat.sales.error.SalesError
import com.musinsa.stat.search.dto.Brand
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet

object BrandRowMapper : RowMapper<Brand> {
    override fun mapRow(rs: ResultSet, rowNum: Int): Brand {
        return Brand(
            brandType = rs.getString("브랜드분류"),
            brandId = rs.getString("브랜드"),
            brandName = rs.getString("브랜드명"),
            brandNameEn = rs.getString("브랜드명_영문"),
            used = when (rs.getString("사용여부")) {
                "N" -> false
                "Y" -> true
                else -> SalesError.UNKNOWN_QUERY_RESULT_VALUE.throwMe()
            },
            isGlobal = when (rs.getInt("글로벌 여부")) {
                0 -> false
                1 -> true
                else -> SalesError.UNKNOWN_QUERY_RESULT_VALUE.throwMe()
            }
        )
    }
}
