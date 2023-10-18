package com.musinsa.stat.sales.domain

/**
 * 업체 구분. code 값은 DB(데이터브릭스)에서 사용 된다.
 */
enum class PartnerType(val code: Int, val description: String) {
    SUPPLY(1, "공급"),
    STORE(2, "입점"),
    AFFILIATE(3, "제휴"),
    SALES(4, "판매"),
    HEAD(9, "본사")
}
