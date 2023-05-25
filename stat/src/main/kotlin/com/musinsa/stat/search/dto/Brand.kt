package com.musinsa.stat.search.dto

data class Brand(
    /**
     * 브랜드분류(S:시스템,U:사용자,G:해외전용)
     */
    val brandType: String,

    /**
     * 브랜드
     */
    val brandId: String,

    /**
     * 브랜드명
     */
    val brandName: String,

    /**
     * 브랜드명_영문
     */
    val brandNameEn: String,

    /**
     * 사용여부(Y/N)
     */
    val used: Boolean,

    /**
     * 글로벌 여부(True/False)
     */
    val isGlobal: Boolean
)