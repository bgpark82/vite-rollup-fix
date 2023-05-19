package com.musinsa.stat.sales.domain

import com.musinsa.stat.sales.dto.Goods
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet

object GoodsRowMapper : RowMapper<Goods> {
    override fun mapRow(rs: ResultSet, rowNum: Int): Goods {
        return Goods(
            rs,
            goodsNumber = rs.getString("상품번호"),
            goodsName = rs.getString("상품명"),
            styleNumber = rs.getString("스타일넘버"),
            brandName = rs.getString("브랜드명"),
            category = rs.getString("카테고리"),
            mdId = rs.getString("담당MD"),
            goodsStatusName = rs.getString("상품상태")
        )
    }
}