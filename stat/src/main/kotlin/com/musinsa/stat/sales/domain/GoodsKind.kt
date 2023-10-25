package com.musinsa.stat.sales.domain

/**
 * 품목 (상품 옵션)
 */
enum class GoodsKind(val description: String) {
    CLOTHES("CLOTHES"),
    BEAUTY("BEAUTY"),
    TICKET("TICKET"),
    SHOES("SHOES"),
    BAG("BAG"),
    WATCH("WATCH"),
    CAP("CAP"),
    ETC("ETC"),
    ACCESSORY("ACCESSORY")
}
