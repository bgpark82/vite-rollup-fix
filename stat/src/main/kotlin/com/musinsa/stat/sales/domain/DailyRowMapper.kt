package com.musinsa.stat.sales.domain

import com.musinsa.stat.sales.dto.Daily
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet

/**
 * 매출통계 RowMapper
 */
object DailyRowMapper : RowMapper<Daily> {
    override fun mapRow(rs: ResultSet, rowNum: Int): Daily {
        return Daily(
            rs,
            date = rs.getString("date") ?: "SUM"
        )
    }
}