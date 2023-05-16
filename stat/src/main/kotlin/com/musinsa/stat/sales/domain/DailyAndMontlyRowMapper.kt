package com.musinsa.stat.sales.domain

import com.musinsa.stat.sales.dto.DailyAndMontly
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet

/**
 * 매출통계 RowMapper
 */
object DailyAndMontlyRowMapper : RowMapper<DailyAndMontly> {
    override fun mapRow(rs: ResultSet, rowNum: Int): DailyAndMontly {
        return DailyAndMontly(
            rs,
            date = rs.getString("date") ?: "SUM"
        )
    }
}