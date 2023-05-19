package com.musinsa.stat.sales.domain

import com.musinsa.stat.sales.dto.Ad
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet

object AdRowMapper : RowMapper<Ad> {
    private const val NULL_VALUE = "값없음"
    override fun mapRow(rs: ResultSet, rowNum: Int): Ad {
        return Ad(
            rs,
            adCode = rs.getString("광고코드") ?: NULL_VALUE,
            adType = rs.getString("광고구분") ?: NULL_VALUE,
            adName = rs.getString("광고명") ?: NULL_VALUE
        )
    }
}