package com.musinsa.stat.sales.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import java.sql.ResultSet

// 썸네일 이미지 CDN 서버
const val IMAGE_MUSINSA_CDN = "https://image.msscdn.net"

data class Goods(
    @JsonIgnore
    val rs: ResultSet,

    /**
     * 상품번호
     */
    val goodsNumber: String,

    /**
     * 상품명
     */
    val goodsName: String,

    /**
     * 스타일넘버
     */
    val styleNumber: String,

    /**
     * 브랜드명
     */
    val brandName: String,

    /**
     * 카테고리
     */
    val category: String,

    /**
     * 담당MD
     */
    val mdId: String,

    /**
     * 상품상태
     */
    val goodsStatusName: String,

    /**
     * 상품 썸네일
     */
    var thumbnail: String
) : SalesStatisticsMetric(rs) {
    init {
        thumbnail = IMAGE_MUSINSA_CDN.plus(this.thumbnail)
    }
}
