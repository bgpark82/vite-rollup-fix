package com.musinsa.stat.search.dto

data class Partner(
    /**
     * 업체구분(1:공급,2:입점,3:제휴,4:판매,9:본사)
     */
    val partnerType: String,

    /**
     * 업체 ID
     */
    val partnerId: String,

    /**
     * 업체명
     */
    val partnerName: String,

    /**
     * 대표자
     */
    val ceo: String,

    /**
     * 사업등록번호
     */
    val businessLicenseNumber: String,

    /**
     * 담당MD 이름
     */
    val mdName: String
)