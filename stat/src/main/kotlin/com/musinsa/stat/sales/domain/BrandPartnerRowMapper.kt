package com.musinsa.stat.sales.domain

import com.musinsa.stat.sales.dto.BrandPartner
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet

object BrandPartnerRowMapper : RowMapper<BrandPartner> {
    override fun mapRow(rs: ResultSet, rowNum: Int): BrandPartner {
        return BrandPartner(
            rs,
            brandId = rs.getString("브랜드"),
            brandName = rs.getString("브랜드명"),
            partnerId = rs.getString("업체코드"),
            mdId = rs.getString("담당MD")
        )
    }
}