package com.musinsa.stat.search.dto

import com.musinsa.stat.util.ObjectMapperFactory.readValues

fun 아디다스_업체_목록(): List<Partner> {
    return readValues(
        """
            [
                {
                    "partnerType": "1",
                    "partnerId": "adidas",
                    "partnerName": "adidas",
                    "ceo": "에드워드닉슨",
                    "businessLicenseNumber": "2148107412",
                    "mdName": "심민경"
                },
                {
                    "partnerType": "2",
                    "partnerId": "adidaseyewear",
                    "partnerName": "adidaseyewear",
                    "ceo": "최선웅",
                    "businessLicenseNumber": "1138616549",
                    "mdName": "주세준"
                },
                {
                    "partnerType": "2",
                    "partnerId": "adidaskorea",
                    "partnerName": "adidaskorea",
                    "ceo": "KWAK KYN YUP PETER(곽",
                    "businessLicenseNumber": "2148107412",
                    "mdName": "박유진"
                },
                {
                    "partnerType": "1",
                    "partnerId": "luckybox_adidas",
                    "partnerName": "luckybox_adidas",
                    "ceo": "조만호",
                    "businessLicenseNumber": "2118879575",
                    "mdName": "임시 MD"
                },
                {
                    "partnerType": "2",
                    "partnerId": "test_adidas",
                    "partnerName": "test_adidas",
                    "ceo": "테스트",
                    "businessLicenseNumber": "2118879575",
                    "mdName": "박유진"
                }
            ]
        """.trimIndent(), Array<Partner>::class.java
    )
}