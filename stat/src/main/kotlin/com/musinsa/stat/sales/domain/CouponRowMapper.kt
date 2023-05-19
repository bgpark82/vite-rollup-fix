package com.musinsa.stat.sales.domain

import com.musinsa.stat.sales.dto.Coupon
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet

object CouponRowMapper : RowMapper<Coupon> {
    override fun mapRow(rs: ResultSet, rowNum: Int): Coupon {
        return Coupon(
            rs,
            couponNumber = rs.getString("쿠폰번호"),
            couponTypeDescription = rs.getString("쿠폰구분"),
            couponName = rs.getString("쿠폰명"),
            couponApplyType = rs.getString("쿠폰사용구분"),
            couponIssueAmount = rs.getString("발행금액")
        )
    }
}