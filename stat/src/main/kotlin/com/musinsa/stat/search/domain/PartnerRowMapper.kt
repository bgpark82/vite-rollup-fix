package com.musinsa.stat.search.domain

import com.musinsa.stat.search.dto.Partner
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet

object PartnerRowMapper : RowMapper<Partner> {
    override fun mapRow(rs: ResultSet, rowNum: Int): Partner {
        return Partner(
            partnerType = rs.getString("업체구분"),
            partnerId = rs.getString("업체ID"),
            partnerName = rs.getString("업체명"),
            ceo = rs.getString("대표자명"),
            businessLicenseNumber = rs.getString("사업등록번호"),
            mdName = rs.getString("담당MD")
        )
    }
}