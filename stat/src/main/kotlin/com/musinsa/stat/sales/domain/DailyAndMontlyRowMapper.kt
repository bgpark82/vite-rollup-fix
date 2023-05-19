package com.musinsa.stat.sales.domain

import com.musinsa.stat.sales.dto.DailyAndMontly
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet

/**
 * 일별/월별매출통계 RowMapper
 */
object DailyAndMontlyRowMapper : RowMapper<DailyAndMontly> {
    override fun mapRow(rs: ResultSet, rowNum: Int): DailyAndMontly {
        return DailyAndMontly(
            rs,
            // TODO SUM 기본값 삭제
            date = rs.getString("date") ?: "SUM"
        )
    }
}