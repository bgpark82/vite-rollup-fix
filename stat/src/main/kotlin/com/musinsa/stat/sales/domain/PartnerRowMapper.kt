package com.musinsa.stat.sales.domain

import com.musinsa.stat.sales.dto.Partner
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet

/**
 * 업체별 RowMapper
 */
object PartnerRowMapper : RowMapper<Partner> {
    override fun mapRow(rs: ResultSet, rowNum: Int): Partner {
        return Partner(
            rs,
            partnerId = rs.getString("업체코드"),
            partnerName = rs.getString("업체명"),
            partnerType = rs.getString("업체구분")
        )
    }
}